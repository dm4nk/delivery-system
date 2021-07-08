package com.company.Mains;

import com.company.controller.Dijkstra;
import com.company.controller.OrdersParser;
import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongTaskFormatException;
import com.company.controller.Parser;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.OrdersSchedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class MainDataset {
    public static void main(String[] args) throws IOException, WrongGraphFormatException, ParseException, WrongTaskFormatException, org.json.simple.parser.ParseException {
        long start = System.currentTimeMillis();

        String path = "src\\main\\resources\\dataset\\";
        Graph.getInstance().readGraphFromFile(path + "nodes.csv", path + "edges.csv");

        Vertex NS = Dijkstra.calculateNearestVertex(Graph.getInstance(), -37.7738026, 144.9836466);
        Vertex TP = Dijkstra.calculateNearestVertex(Graph.getInstance(), -37.8618349, 144.905716);
        Vertex BK = Dijkstra.calculateNearestVertex(Graph.getInstance(), -37.8158343, 145.04645);
        String[] restaurantStreetIDs = new String[]{NS.getName(), TP.getName(), BK.getName()};

        OrdersSchedule melbourneOrders = new OrdersSchedule();

        Parser parser = new OrdersParser();
        parser.parseTo(new File(path + "orders.json"), melbourneOrders);

        melbourneOrders.write2BestPathsFor(Graph.getInstance(), melbourneOrders.getOrder(0), restaurantStreetIDs);

        long end = System.currentTimeMillis();
        System.out.println("\nTIME: " + (double)(end-start)/1000);
    }
}
