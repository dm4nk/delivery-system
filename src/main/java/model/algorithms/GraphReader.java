package model.algorithms;

import com.opencsv.CSVReader;
import exceptions.WrongGraphFormatException;
import model.graph.Graph;

import java.io.*;

import static java.lang.Math.sqrt;

/**
 * Contains static methods for reading graph from .txt and .csv files
 */
public class GraphReader {

    private GraphReader() {
    }

    /**
     * reads file with n*n matrix and returns matrix according to it
     *
     * @param file txt file to read from
     * @return matrix, according to file read
     * @throws IOException               error opening file
     * @throws WrongGraphFormatException matrix is not n*n, contains characters other than positive doubles
     */
    public static double[][] readGraph(File file) throws IOException, WrongGraphFormatException {
        //позволяет считать числа, не зная их количество
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        //разбиваем строку на массив строк, в каждой их которых содержится 1 число
        String[] valueStr = new String(bytes).trim().split("\\s+");

        //проверка на размер графа, должен быть n*n
        int graphSize = (int) sqrt(valueStr.length);
        if (sqrt(valueStr.length) != graphSize || graphSize < 2) {
            throw new WrongGraphFormatException("wrong Graph size:" + graphSize);
        }

        double[][] graph = new double[graphSize][graphSize];
        for (int i = 0; i < graphSize; ++i)
            for (int j = 0; j < graphSize; ++j) {
                if ((graph[i][j] = Integer.parseInt(valueStr[i * graphSize + j])) < 0)//получаем доступ к i j элементу в строке...
                    throw new WrongGraphFormatException("negative value in Graph");
                if (graph[i][j] == 0) graph[i][j] = Double.MAX_VALUE;
            }
        return graph;
    }

    /**
     * reads nodes and edges to graph
     *
     * @param nodes .csv file with nodes.
     *              format: id, latitude, longitude
     * @param edges .csv file with edges
     *              format: id, id of source vertex, id of target vertex, length in meters, type of street according to speed limit, speed limit
     * @throws IOException               error while opening or reading file
     * @throws WrongGraphFormatException if files contain duplicates
     */
    public static void readGraph(File nodes, File edges, Graph graph) throws WrongGraphFormatException, IOException {
        if (!nodes.getName().endsWith(".csv") || !edges.getName().endsWith(".csv"))
            throw new FileNotFoundException("can only read from .csv files");

        try (CSVReader csvReader = new CSVReader(new FileReader(nodes))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                graph.addVertex(
                        Long.parseLong(values[0]),
                        Double.parseDouble(values[2]),
                        Double.parseDouble(values[1])
                );
            }
        }

        try (CSVReader csvReader = new CSVReader(new FileReader(edges))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                graph.addEdge(
                        0.06 * Double.parseDouble(values[3]) / Double.parseDouble(values[5]),
                        Long.parseLong(values[1]),
                        Long.parseLong(values[2])
                );
            }
        }
    }

    /**
     * makes graph from matrix n*n.
     * <p>
     * if there is no path between 2 vertices, write 0 in matrix
     *
     * @param file .txt file with matrix
     * @throws IOException               error while reading file
     * @throws WrongGraphFormatException matrix is not n*n, contains characters other than positive doubles
     */
    public static void readGraph(File file, Graph graph) throws IOException, WrongGraphFormatException {
        double[][] matrix = GraphReader.readGraph(file);

        for (int i = 0; i < matrix.length; ++i)
            graph.addVertex(i);

        for (int i = 0; i < matrix.length; ++i)
            for (int j = 0; j < matrix.length; ++j)
                if (matrix[i][j] != Double.MAX_VALUE)
                    graph.addEdge(matrix[i][j], i, j);
    }
}