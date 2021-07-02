package com.company.Mains;

import com.company.controller.tasksParser;
import com.company.model.schedules.tasksSchedule;
import com.company.model.graph.graph;

import java.io.File;

public class MainDynamic {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        String path = "C:\\Users\\dimxx\\IdeaProjects\\magenta_test\\src\\main\\resources\\other\\";

        tasksSchedule DelovieLinii = new tasksSchedule();

        tasksParser.parseTo(new File(path + "tasks.json"), DelovieLinii);

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