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

    public ConsolidatedOrderSchedule(){
        routes = new LinkedList<>();
    }

    public ConsolidatedOrderSchedule(Graph graph, OrdersSchedule ordersSchedule) throws WrongOrderFormatException {
        routes = new LinkedList<>();
        for(int i = 0; i < ordersSchedule.size(); ++i){
            addOrder(graph, ordersSchedule.getOrder(i));
        }
    }

    @Override
    public void addOrder(Graph graph, Order order) throws WrongOrderFormatException {
        order.setVertex(graph);

        double minDistance = Double.MAX_VALUE;
        Route minDistanceRoot = null;
        for (Route r : routes) {
            if (r.calculateDistanceToLastOrder(graph, order) < minDistance) {
                minDistance = r.calculateDistanceToLastOrder(graph, order);
                minDistanceRoot = r;
            }
        }

        if(minDistanceRoot == null) routes.add(new Route(order));
        else if(minDistance < 15 && minDistanceRoot.add(order));
        else routes.add(new Route(order));
    }

    @Override
    public void writePaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        writeConsolidatedPaths(graph, fromVertices);
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

    public List<List<Vertex>> writeConsolidatedPaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        List<List<Vertex>> consolidatedPaths = new ArrayList<>();

        for(Route r: routes){
            System.out.println("\nRoute: ");
            consolidatedPaths.add(r.writeBestPath(graph, fromVertices));
        }

        routes.removeAll(routes);

        return consolidatedPaths;
    }
}
