package com.company.Mains;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.graph.graph;
import com.company.schedules.ordersSchedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class MainDataset {
    public static void main(String[] args) throws IOException, wrongGraphFormatException, ParseException, wrongTaskFormatException, org.json.simple.parser.ParseException {
        long start = System.currentTimeMillis();

        String restaurantStreetID = "711327755";

        graph.getInstance().readGraphFromFile("nodes.csv", "edges.csv");

        ordersSchedule orders = ordersSchedule.createOrdersFromJSONFile(new File("orders.json"));

        orders.writeBestPathFor(graph.getInstance(), orders.getOrder(1), restaurantStreetID);

        long end = System.currentTimeMillis();
        System.out.println("\nTIME: " + (double)(end-start)/1000);
    }
}
