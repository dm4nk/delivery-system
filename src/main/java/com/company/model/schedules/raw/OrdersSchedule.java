package com.company.model.schedules.raw;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.Order;
import com.company.model.schedules.Schedule;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

public class OrdersSchedule implements Schedule, Serializable {
    Map<String, Order> orders;
    List<String> orderIDs;

    private OrdersSchedule(Map<String, Order> orders, LinkedList<String> orderIDs){
        this.orders = orders;
        this.orderIDs = orderIDs;
    }

    public static OrdersSchedule create(Map<String, Order> orders, LinkedList<String> orderIDs){
        return new OrdersSchedule(orders, orderIDs);
    }

    public static OrdersSchedule create(){
        return new OrdersSchedule(new HashMap<>(), new LinkedList<>());
    }

    public int size(){
        return orders.size();
    }

    public List<Vertex> writeBestPathFor(Graph graph, Order order, Vertex fromRestaurant) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(order, fromRestaurant);
    }

    public List<Vertex> writeBestPathFor(Graph graph, Order order, List<Vertex> fromRestaurants) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(order, fromRestaurants);
    }

    public List<Vertex> write2BestPathsFor(Graph graph, Order order, Vertex fromRestaurant) throws WrongOrderFormatException, ParseException {
        List<Vertex> vert = new ArrayList<>(1);
        vert.add(fromRestaurant);
        return graph.write2PathsAndTime(order, vert);
    }

    public List<Vertex> write2BestPathsFor(Graph graph, Order order, List<Vertex> fromRestaurants) throws WrongOrderFormatException, ParseException {
        return graph.write2PathsAndTime(order, fromRestaurants);
    }

    public Order getOrder(String orderID){
        return orders.get(orderID);
    }

    public Order getOrder(int index){
        return orders.get(orderIDs.get(index));
    }

    @Override
    public void addOrder(Graph graph, Order order) throws WrongOrderFormatException {
        order.setNearestVertex(graph);
        if(orders.put(order.getId(), order)!= null) throw new WrongOrderFormatException("such Order already exists");
        orderIDs.add(order.getId());
    }

    @Override
    public void writePaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        for(Order o : orders.values()) {
            System.out.println("\nOrder: ");
            write2BestPathsFor(graph, o, fromVertices);
        }
    }
}
