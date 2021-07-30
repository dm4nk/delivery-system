package mains;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.algorithms.Dijkstra;
import model.algorithms.Parser;
import model.algorithms.impl.OrdersParser;
import model.graph.Graph;
import model.graph.Vertex;
import model.schedule.FactoryType;
import model.schedule.Schedule;
import model.schedule.ScheduleFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, WrongGraphFormatException, ParseException, WrongOrderFormatException, org.json.simple.parser.ParseException {
        Date start = new Date();

        String path = "src\\main\\resources\\dataset\\";
        Graph.getInstance().readGraphFromFile(new File(path + "nodes.csv"), new File(path + "edges.csv"));

        //find restaurant street
        Vertex NS = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(), -37.7738026, 144.9836466);
        Vertex TP = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(), -37.8618349, 144.905716);
        Vertex BK = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(), -37.8158343, 145.04645);

        List<Vertex> restaurants = new ArrayList<>(3);
        restaurants.add(NS);
        restaurants.add(TP);
        restaurants.add(BK);

        ScheduleFactory factory = ScheduleFactory.create(FactoryType.CONSOLIDATED);
        Schedule melbourneOrders = factory.createSchedule();

        Parser parser = new OrdersParser();
        parser.parseTo(new File(path + "consOrders.json"), melbourneOrders, Graph.getInstance());

        melbourneOrders.writePaths(Graph.getInstance(), restaurants);

        Date end = new Date();
        System.out.println("\nTIME: " + (end.getTime() - start.getTime()) / 1000d);
    }
}