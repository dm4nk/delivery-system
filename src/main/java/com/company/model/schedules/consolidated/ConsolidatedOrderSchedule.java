package com.company.model.schedules.consolidated;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.Schedule;
import com.company.model.schedules.Order;
import com.company.model.schedules.raw.OrdersSchedule;

import java.text.ParseException;
import java.util.*;

public class ConsolidatedOrderSchedule implements Schedule {
    private List<Route> routes;
    private static final double MAX_DISTANCE_FOR_CONSOLIDATION = 0.05;//5 km

    private ConsolidatedOrderSchedule(){
        routes = new LinkedList<>();
    }

    private ConsolidatedOrderSchedule(Graph graph, OrdersSchedule ordersSchedule) throws WrongOrderFormatException {
        routes = new LinkedList<>();
        for(int i = 0; i < ordersSchedule.size(); ++i){
            addOrder(graph, ordersSchedule.getOrder(i));
        }
    }

    public static ConsolidatedOrderSchedule create(){
        return new ConsolidatedOrderSchedule();
    }

    public static ConsolidatedOrderSchedule createAndConsolidate(Graph graph, OrdersSchedule ordersSchedule) throws WrongOrderFormatException {
        return new ConsolidatedOrderSchedule(graph, ordersSchedule);
    }

    /**
     * @return root with nearest last order
     * or null if nearest order is more than 5km away
     */
    private Route findMostSuitableRoot(Order order){
        double minDistance = Double.MAX_VALUE;
        Route minDistanceRoot = null;
        for (Route r : routes) {
            if (r.calculateDistanceToLastOrder(order) < minDistance) {
                minDistance = r.calculateDistanceToLastOrder(order);
                minDistanceRoot = r;
            }
        }

        if(minDistance > MAX_DISTANCE_FOR_CONSOLIDATION) return null;
        return minDistanceRoot;
    }

    public Route getRoute(int index){
        return routes.get(index);
    }

    public Route remove(int index){
        return routes.remove(index);
    }

    public void writeOrders(){
        routes.forEach(r -> {
            r.writeRoute();
            System.out.println();
        });
    }

    public List<Vertex> writeConsolidatedPath(Graph graph, Route route, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return route.writeBestPath(graph, fromVertices);
    }

    @Override
    public void addOrder(Graph graph, Order order) throws WrongOrderFormatException {
        order.setNearestVertex(graph);
        Route minDistanceRoot = findMostSuitableRoot(order);
        if(minDistanceRoot == null || !minDistanceRoot.add(order)) routes.add(Route.create(order));
    }

    @Override
    public void writePaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        for(Route r: routes){
            System.out.println("\nRoute: ");
            writeConsolidatedPath(graph, r, fromVertices);
        }
    }
}
