package com.company.model.schedules.consolidated;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.Schedule;
import com.company.model.schedules.Order;

import java.text.ParseException;
import java.util.*;

public class ConsolidatedOrderSchedule implements Schedule {
    private Queue<Route> routes;

    public ConsolidatedOrderSchedule(){
        routes = new LinkedList<>();
    }

    @Override
    public void addOrder(Order order){
        boolean isAdded = false;
        //order.setStreet(Graph.getInstance());//todo: перенести это в конструктор ордеров и переписать всю прогу под это
        for(Route r : routes){
            if(r.add(order)){
                isAdded = true;
                break;
            }
        }
        if(!isAdded) {
            Route tmp = new Route();
            tmp.add(order);
            routes.add(tmp);
        }
    }

    public void removeRoute(){
        routes.remove();
    }

    public Route pollRoute(){
        return routes.poll();
    }

    public void writeOrders(){
        routes.forEach(r -> {
            r.writeRoute();
            System.out.println();
        });
    }

    public List<List<Vertex>> writeConsolidatedPaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        List<List<Vertex>> consolidatedPaths = new ArrayList<>();

        while(!routes.isEmpty()){
            System.out.println("\nRoute: ");
            consolidatedPaths.add(routes.poll().writeBestPath(graph, fromVertices));
        }
        return consolidatedPaths;
    }
}
