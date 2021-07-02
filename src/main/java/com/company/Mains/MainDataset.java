package com.company.Mains;

import com.company.controller.ordersParser;
import com.company.model.Exceptions.wrongGraphFormatException;
import com.company.model.Exceptions.wrongTaskFormatException;
import com.company.model.graph.graph;
import com.company.model.schedules.ordersSchedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class MainDataset {
    public static void main(String[] args) throws IOException, wrongGraphFormatException, ParseException, wrongTaskFormatException, org.json.simple.parser.ParseException {
        long start = System.currentTimeMillis();

        String restaurantStreetID = "711327755";
        String path = "C:\\Users\\dimxx\\IdeaProjects\\magenta_test\\src\\main\\resources\\dataset\\";

        graph.getInstance().readGraphFromFile(path + "nodes.csv", path + "edges.csv");

        ordersSchedule melbourneOrders = new ordersSchedule();

        ordersParser.parseTo(new File(path + "orders.json"), melbourneOrders);

        melbourneOrders.writeBestPathFor(graph.getInstance(), melbourneOrders.getOrder(1), restaurantStreetID);

        long end = System.currentTimeMillis();
        System.out.println("\nTIME: " + (double)(end-start)/1000);
    }
}
