package com.company.model.schedules.consolidated;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.Order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Route {
    private List<Order> route;

    Route(){
        route = new LinkedList<>();
    }

    public boolean add(Order order){
        if(route.size() == 0 ){
            route.add(order);
            return true;
        }
        if(route.size() == 3) return false;
        if(route.get(route.size()-1).getDistanceTo(order) > 0.005) return false;

        route.add(order);
        return true;
    }

    public Order getOrder(int i){
        return route.get(i);
    }

    public int size(){
        return route.size();
    }

    public void writeRoute(){
        route.forEach(v ->
                System.out.print(v.getId()+ " -> ")
        );
    }

    public List<Vertex> writeBestPath(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        route.get(0).setStreet(graph);
        List<Vertex> path = new ArrayList<>(route.get(0).writeBestPath(graph, fromVertices));

        Vertex fromStreet = route.get(0).getStreet();

        for(int i = 1; i < route.size(); ++i){
            route.get(i).setStreet(graph);
            System.out.println();
            route.get(i).setDispatchTime(route.get(i-1).getArrivalTime());
            path.addAll( route.get(i).writeBestPath(graph, fromStreet));
            fromStreet = route.get(i).getStreet();
        }

        route = null;
        return path;
    }
}
