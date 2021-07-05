package com.company.Mains;

import com.company.controller.TasksParser;
import com.company.model.graph.Graph;
import com.company.model.schedules.TasksSchedule;

import java.io.File;

public class MainDynamic {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        String path = "C:\\Users\\dimxx\\IdeaProjects\\magenta_test\\src\\main\\resources\\other\\";

        TasksSchedule DelovieLinii = new TasksSchedule();

        TasksParser.parseTo(new File(path + "tasks.json"), DelovieLinii);

        Graph mapOfSamaraOblast = Graph.getInstance();

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