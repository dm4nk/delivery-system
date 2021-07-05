package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Graph;

import java.util.*;

public class OrdersSchedule {
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

    public void writeBestPathFor(Graph graph, Order order, String restaurantStreetID) throws WrongTaskFormatException {
        order.writePathAndTime(graph, restaurantStreetID);
    }

    public Order getOrder(String orderID){
        return (Order) orders.get(orderID);
    }

    public Order getOrder(int orderNumber){
        return (Order) orders.get(orderIDs.get(orderNumber));
    }
}
