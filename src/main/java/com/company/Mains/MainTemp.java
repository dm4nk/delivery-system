package com.company.Mains;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongOrderFormatException;
import com.company.algorithms.Dijkstra;
import com.company.algorithms.OrdersParser;
import com.company.algorithms.Parser;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.Schedule;
import com.company.model.schedules.raw.OrdersSchedule;
import com.company.model.schedules.consolidated.ConsolidatedOrderSchedule;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainTemp {
    public static void main(String[] args) throws WrongGraphFormatException, IOException, ParseException, java.text.ParseException, WrongOrderFormatException {

        String path = "src\\main\\resources\\";
        Graph.getInstance().readGraphFromFile(path + "dataset\\nodes.csv", path + "dataset\\edges.csv");

        Vertex NS = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(),  144.9836466, -37.7738026);
        Vertex TP = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(),144.905716, -37.8618349);
        Vertex BK = Dijkstra.calculateNearestVertexFromLatLon(Graph.getInstance(), 145.04645, -37.8158343);

        List<Vertex> restaurants = new ArrayList<>(3);
        restaurants.add(NS);
        restaurants.add(TP);
        restaurants.add(BK);

        ConsolidatedOrderSchedule schedule = new ConsolidatedOrderSchedule();
        Parser parser = new OrdersParser();
        parser.parseTo(new File(path + "other\\consOrders.json"), schedule);

        schedule.writeConsolidatedPaths(Graph.getInstance(), restaurants);
    }
}