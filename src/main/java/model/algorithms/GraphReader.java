package model.algorithms;

import exceptions.WrongGraphFormatException;
import model.dto.DTO;
import model.graph.Edge;
import model.graph.Graph;
import model.graph.Vertex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
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
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        String[] valueStr = new String(bytes).trim().split("\\s+");

        int graphSize = (int) sqrt(valueStr.length);
        if (sqrt(valueStr.length) != graphSize || graphSize < 2) {
            throw new WrongGraphFormatException("wrong Graph size:" + graphSize);
        }

        double[][] graph = new double[graphSize][graphSize];
        for (int i = 0; i < graphSize; ++i)
            for (int j = 0; j < graphSize; ++j) {
                if ((graph[i][j] = Integer.parseInt(valueStr[i * graphSize + j])) < 0)
                    throw new WrongGraphFormatException("negative value in Graph");
                if (graph[i][j] == 0) graph[i][j] = Double.MAX_VALUE;
            }
        return graph;
    }

    /**
     * Fills graph with vertices from DTO lists.
     *
     * @param vertices list of DTO.vertex
     * @param edges    list of DTO.edge
     * @param graph    graph to work with
     * @throws WrongGraphFormatException if list of vertices contains duplicates
     */
    public static void readGraph(List<DTO.vertex> vertices, List<DTO.edge> edges, Graph graph) throws WrongGraphFormatException {
        for (DTO.vertex v : vertices)
            graph.addVertex(
                    Vertex.create(
                            parseLong(v.getId()),
                            parseDouble(v.getLat()),
                            parseDouble(v.getLon()))
            );

        for (DTO.edge e : edges)
            graph.addEdge(
                    Edge.create(
                            0.06 * parseDouble(e.getLength()) / parseDouble(e.getMaxSpeed()),
                            graph.getVertex(parseLong(e.getSourceVertex())),
                            graph.getVertex(parseLong(e.getTargetVertex()))
                    )
            );
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
        if (!file.getName().endsWith(".txt")) throw new FileNotFoundException(file.getName() + " is not a .txt format");
        double[][] matrix = readGraph(file);

        for (int i = 0; i < matrix.length; ++i)
            graph.addVertex(Vertex.create(i));

        for (int i = 0; i < matrix.length; ++i)
            for (int j = 0; j < matrix.length; ++j)
                if (matrix[i][j] != Double.MAX_VALUE)
                    graph.addEdge(
                            Edge.create(
                                    matrix[i][j],
                                    graph.getVertex(i),
                                    graph.getVertex(j)
                            )
                    );
    }
}