package com.company.Mains;

import com.company.schedules.taskSchedule;
import com.company.graph.graph;

import java.io.File;

public class MainDynamic {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        taskSchedule DelovieLinii = taskSchedule.createScheduleFromFile(new File("tasks.json"));

        graph mapOfSamaraOblast = graph.getInstance();

        mapOfSamaraOblast.addVertex("Samara");
        mapOfSamaraOblast.addVertex("TLT");
        mapOfSamaraOblast.addVertex("Moscow");

        mapOfSamaraOblast.addEdge(10.0, "Samara", "TLT");
        mapOfSamaraOblast.addEdge(11.0, "TLT", "Moscow");
        mapOfSamaraOblast.addEdge(99.0, "Samara", "Moscow");

        DelovieLinii.writeBestPath(mapOfSamaraOblast, 0);

        long end = System.currentTimeMillis();

        System.out.println("\nTIME: " + (double)(end-start)/1000);
    }
}