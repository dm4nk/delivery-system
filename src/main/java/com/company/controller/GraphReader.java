package com.company.controller;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.model.graph.Graph;
import com.opencsv.CSVReader;

import java.io.*;

import static java.lang.Math.sqrt;

public class GraphReader {

    private GraphReader(){}

    public static double[][]readGraph(String filename) throws IOException, WrongGraphFormatException {
        //позволяет считать числа, не зная их количество
        File file = new File(filename);
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        //разбиваем строку на массив строк, в каждой их которых содержится 1 число
        String[] valueStr = new String(bytes).trim().split("\\s+");

        //проверка на размер графа, должен быть n*n
        int graphSize = (int) sqrt( valueStr.length );
        if( sqrt( valueStr.length ) != graphSize || graphSize < 2){
            throw new WrongGraphFormatException("wrong Graph size:" + graphSize);
        }

        double[][] graph = new double[graphSize][graphSize];
        for (int i = 0; i < graphSize; ++i)
            for (int j = 0; j < graphSize; ++j) {
                if ((graph[i][j] = Integer.parseInt(valueStr[i * graphSize + j])) < 0)//получаем доступ к i j элементу в строке...
                    throw new WrongGraphFormatException("negative value in Graph");
                    if(graph[i][j] == 0) graph[i][j] = Double.MAX_VALUE;
            }
        return graph;
    }

    public static void readGraph(String nodes, String edges, Graph graph) throws WrongGraphFormatException, IOException {
        if(!nodes.endsWith(".csv") || !edges.endsWith(".csv"))
            throw new FileNotFoundException("can only read from .csv files");

        try (CSVReader csvReader = new CSVReader(new FileReader(nodes))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                graph.addVertex(
                        values[0],
                        Double.parseDouble(values[1]),
                        Double.parseDouble(values[2])
                );
            }
        }

        try (CSVReader csvReader = new CSVReader(new FileReader(edges))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
               graph.addEdge(0.06*Double.parseDouble(values[3])/Double.parseDouble(values[5]), values[1], values[2]);
            }
        }
    }

    public static void readGraph(String filename, Graph graph) throws IOException, WrongGraphFormatException {
        double[][] matrix = GraphReader.readGraph(filename);

        for(int i = 0; i < matrix.length; ++i)
            graph.addVertex(Double.toString(i));

        for(int i = 0; i < matrix.length; ++i)
            for (int j = 0; j < matrix.length; ++j)
                if(matrix[i][j] != Double.MAX_VALUE )
                    graph.addEdge(matrix[i][j], Double.toString(i), Double.toString(j));
    }
}
