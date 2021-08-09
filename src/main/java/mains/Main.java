package mains;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.algorithms.EdgeParser;
import model.algorithms.OrdersParser;
import model.algorithms.VertexParser;
import model.dto.DTO;
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

import static model.algorithms.Dijkstra.calculateNearestVertexFromLatLon;

public class Main {
    public static void main(String[] args) throws IOException, WrongGraphFormatException, ParseException, WrongOrderFormatException {
        Date start = new Date();

        String path = "src\\main\\resources\\dataset\\";
        List<DTO.vertex> vertices = VertexParser.parse(new File(path + "nodes.csv"));
        List<DTO.edge> edges = EdgeParser.parse(new File(path + "edges.csv"));

        Graph.getInstance().readGraphFromDTOs(vertices, edges);

        //find restaurant street
        Vertex NS = calculateNearestVertexFromLatLon(Graph.getInstance(), -37.7738026, 144.9836466);
        Vertex TP = calculateNearestVertexFromLatLon(Graph.getInstance(), -37.8618349, 144.905716);
        Vertex BK = calculateNearestVertexFromLatLon(Graph.getInstance(), -37.8158343, 145.04645);

        List<Vertex> restaurants = new ArrayList<>(3);
        restaurants.add(NS);
        restaurants.add(TP);
        restaurants.add(BK);

        ScheduleFactory factory = ScheduleFactory.create(FactoryType.CONSOLIDATED);
        Schedule melbourneOrders = factory.createSchedule();

        List<DTO.order> listOfOrdersDTO = OrdersParser.parse(new File(path + "consOrders.json"));
        melbourneOrders.readFromDTO(Graph.getInstance(), listOfOrdersDTO);

        melbourneOrders.writePaths(Graph.getInstance(), restaurants);

        Date end = new Date();
        System.out.println("\nTIME: " + (end.getTime() - start.getTime()) / 1000d);
    }
}