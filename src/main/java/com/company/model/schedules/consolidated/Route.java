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
    private final List<Order> route;

    private Route(Order order){
        route = new LinkedList<>();
        route.add(order);
    }

    public static Route create(Order order){
        return new Route(order);
    }

    /**
     * @param order - заказ, который нужно добаить
     * @return true, если можно добавить заказ к этому Route (не больше 3 заказов)
     */
    public boolean add(Order order){
        if(route.size() == 3) return false;

        route.add(order);
        return true;
    }

    public double calculateDistanceToLastOrder(Order order){
        return route.get(route.size()-1).getDistanceTo(order);
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
        List<Vertex> path = new ArrayList<>(
                route.get(0).writeBestPath(graph, fromVertices)
        );

        Vertex fromStreet = route.get(0).getVertex();

        for(int i = 1; i < route.size(); ++i){
            System.out.println();
            route.get(i).setDispatchTime(route.get(i-1).getArrivalTime());
            path.addAll(route.get(i).writeBestPath(graph, fromStreet));
            fromStreet = route.get(i).getVertex();
        }

        return path;
    }
}
