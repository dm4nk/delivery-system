package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;

import java.io.Serializable;
import java.util.*;

public class OrdersSchedule implements Schedule, Serializable {
    Map orders;
    ArrayList<String> orderIDs;

    public OrdersSchedule(Map orders, ArrayList<String> orderIDs){
        this.orders = orders;
        this.orderIDs = orderIDs;
    }

    public OrdersSchedule(){
        orders = new HashMap();
        orderIDs = new ArrayList<>();
    }

    public int size(){
        return orders.size();
    }

    public void addOrder(String orderID,  Order order) throws WrongTaskFormatException {
        if(orders.put(orderID, order)!= null) throw new WrongTaskFormatException("such Order already exists");
        orderIDs.add(orderID);
    }

    public List<Vertex> writeBestPathFor(Graph graph, Order order, String restaurantStreetID) throws WrongTaskFormatException {
        return order.writePathAndTime(graph, restaurantStreetID);
    }

    public List<Vertex> writeBestPathFor(Graph graph, Order order, String[] restaurantStreetIDs) throws WrongTaskFormatException {
        return order.writePathAndTime(graph, restaurantStreetIDs);
    }

    public List<Vertex> write2BestPathsFor(Graph graph, Order order, String restaurantStreetID) throws WrongTaskFormatException {
        return order.write2PathsAndTime(graph, restaurantStreetID);
    }

    public List<Vertex> write2BestPathsFor(Graph graph, Order order, String[] restaurantStreetIDs) throws WrongTaskFormatException {
        return order.write2PathsAndTime(graph, restaurantStreetIDs);
    }

    public Order getOrder(String orderID){
        return (Order) orders.get(orderID);
    }

    public Order getOrder(int orderNumber){
        return (Order) orders.get(orderIDs.get(orderNumber));
    }
}
