package com.company.someDijkstra.dijkstraForMatrix;

import com.company.Exceptions.wrongTaskFormatException;
import com.company.schedules.Task;

import java.util.ArrayList;

public abstract class customDijkstraForMatrixGraph extends basicDijkstraForMatrixGraph {

    public static ArrayList<Integer> showPath(double[][] graph, Task task, ArrayList<Integer> path) throws wrongTaskFormatException {

        if (graph.length==2) return null;

        int temp = path.get(path.size()/2);//реализовать на динамической структуре данных
        for(int i = 0; i < graph.length; ++i) {
            graph[i][temp] = Double.MAX_VALUE;
            graph[temp][i] = Double.MAX_VALUE;
        }

        return showPath(graph, task);
    }
}
