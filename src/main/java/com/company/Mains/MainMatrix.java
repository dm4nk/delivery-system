package com.company.Mains;

import com.company.schedules.mySchedule;
import com.company.graphs.graph;
import com.company.graphs.matrix.matrixGraph;

import java.io.File;

public class MainMatrix {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        mySchedule sh = mySchedule.createScheduleFromFile(new File("tasks.json"));

        graph matrixGraph1 = matrixGraph.getInstance();
        matrixGraph1.readGraphFromFile("graph.txt");

        sh.writeBestPath(matrixGraph1, 1);

        long end = System.currentTimeMillis();

        System.out.println("\nTIME: " + (double)(end-start)/1000);
    }
}