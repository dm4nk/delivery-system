package com.company.Mains;

import com.company.controller.OrdersParser;
import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Graph;
import com.company.model.schedules.OrdersSchedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class MainDataset {
    public static void main(String[] args) throws IOException, WrongGraphFormatException, ParseException, WrongTaskFormatException, org.json.simple.parser.ParseException {
        long start = System.currentTimeMillis();

        String restaurantStreetID = "711327755";
        String path = "C:\\Users\\dimxx\\IdeaProjects\\magenta_test\\src\\main\\resources\\dataset\\";

        Graph.getInstance().readGraphFromFile(path + "nodes.csv", path + "edges.csv");

        OrdersSchedule melbourneOrders = new OrdersSchedule();

        OrdersParser.parseTo(new File(path + "orders.json"), melbourneOrders);

        melbourneOrders.writeBestPathFor(Graph.getInstance(), melbourneOrders.getOrder(1), restaurantStreetID);

        long end = System.currentTimeMillis();
        System.out.println("\nTIME: " + (double)(end-start)/1000);
    }
}
