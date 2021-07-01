package com.company;

import com.company.Exceptions.wrongGraphFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
}
