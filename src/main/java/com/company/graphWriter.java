package com.company;

public class graphWriter {
    public static void writeGraph(double[][] graph){
        for (int i = 0; i < graph.length; ++i) {
            for (int j = 0; j < graph.length; ++j)
                if(graph[i][j] != Double.MAX_VALUE) System.out.print(graph[i][j] + " ");
                else System.out.print("." + " ");
            System.out.println();
        }
    }
}
