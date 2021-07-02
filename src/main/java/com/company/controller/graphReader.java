package com.company.controller;

import com.company.model.Exceptions.wrongGraphFormatException;
import com.company.model.graph.graph;
import com.opencsv.CSVReader;

import java.io.*;

import static java.lang.Math.sqrt;

public class graphReader {

    public static double[][]readGraph(String filename) throws IOException, wrongGraphFormatException {
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
            throw new wrongGraphFormatException("wrong graph size:" + graphSize);
        }

        double[][] graph = new double[graphSize][graphSize];
        for (int i = 0; i < graphSize; ++i)
            for (int j = 0; j < graphSize; ++j) {
                if ((graph[i][j] = Integer.parseInt(valueStr[i * graphSize + j])) < 0)//получаем доступ к i j элементу в строке...
                    throw new wrongGraphFormatException("negative value in graph");
                    if(graph[i][j] == 0) graph[i][j] = Double.MAX_VALUE;
            }
        return graph;
    }

    public static void readGraph(String nodes, String edges, graph graph) throws wrongGraphFormatException, IOException {
        if(!nodes.endsWith(".csv") || !edges.endsWith(".csv"))
            throw new wrongGraphFormatException("can only read from .csv files");

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
}
