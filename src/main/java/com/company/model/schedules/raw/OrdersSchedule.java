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
    Queue<String> orderIDs;

    public OrdersSchedule(Map<String, Order> orders, LinkedList<String> orderIDs){
        this.orders = orders;
        this.orderIDs = orderIDs;
    }

    public OrdersSchedule(){
        orders = new HashMap<>();
        orderIDs = new LinkedList<>();
    }

    public int size(){
        return orders.size();
    }

    @Override
    public void addOrder(Order order) throws WrongOrderFormatException {
        if(orders.put(order.getId(), order)!= null) throw new WrongOrderFormatException("such Order already exists");
        orderIDs.add(order.getId());
    }

    @Override
    public void removeOrder(){
        orders.remove(orderIDs.poll());
    }

    public List<Vertex> writeBestPathFor(Graph graph, Order order, Vertex fromRestaurant) throws WrongOrderFormatException, ParseException {
        return order.writeBestPath(graph, fromRestaurant);
    }

    public List<Vertex> writeBestPathFor(Graph graph, Order order, List<Vertex> fromRestaurants) throws WrongOrderFormatException, ParseException {
        return order.writeBestPath(graph, fromRestaurants);
    }

    public List<Vertex> write2BestPathsFor(Graph graph, Order order, Vertex fromRestaurant) throws WrongOrderFormatException, ParseException {
        List<Vertex> vert = new ArrayList<>(1);
        vert.add(fromRestaurant);
        return order.write2PathsAndTime(graph, vert);
    }

    public List<Vertex> write2BestPathsFor(Graph graph, Order order, List<Vertex> fromRestaurants) throws WrongOrderFormatException, ParseException {
        return order.write2PathsAndTime(graph, fromRestaurants);
    }

    public Order getOrder(String orderID){
        return orders.get(orderID);
    }
}
