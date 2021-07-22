package com.company.Mains;

import com.company.algorithms.Dijkstra;
import com.company.algorithms.OrdersParser;
import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongOrderFormatException;
import com.company.algorithms.Parser;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.Schedule;
import com.company.model.schedules.ScheduleFactory;
import com.company.model.schedules.ScheduleType;
import com.company.model.schedules.raw.OrdersSchedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainDataset {
    public static void main(String[] args) throws IOException, WrongGraphFormatException, ParseException, WrongOrderFormatException, org.json.simple.parser.ParseException {

        String path = "src\\main\\resources\\dataset\\";
        Graph.getInstance().readGraphFromFile(path + "nodes.csv", path + "edges.csv");

        //находим ближайшие рестораны
        Vertex NS = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(),  144.9836466, -37.7738026);
        Vertex TP = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(),144.905716, -37.8618349);
        Vertex BK = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(), 145.04645, -37.8158343);

        List<Vertex> restaurants = new ArrayList<>(3);
        restaurants.add(NS);
        restaurants.add(TP);
        restaurants.add(BK);

        ScheduleFactory factory = new ScheduleFactory();
        factory.setFactoryType(ScheduleType.CONSISTENT);
        Schedule melbourneOrders = factory.createSchedule();

        Parser parser = new OrdersParser();
        parser.parseTo(new File(path + "consOrders.json"), melbourneOrders, Graph.getInstance());

        melbourneOrders.writePaths(Graph.getInstance(), restaurants);
    }
}
