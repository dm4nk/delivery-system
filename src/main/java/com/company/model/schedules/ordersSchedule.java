package com.company.model.schedules;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.model.graph.graph;
import java.util.*;

public class ordersSchedule {
    Map orders;
    ArrayList<String> orderIDs;

    public ordersSchedule(Map orders, ArrayList<String> orderIDs){
        this.orders = orders;
        this.orderIDs = orderIDs;
    }

    public ordersSchedule(){
        orders = new HashMap();
        orderIDs = new ArrayList<>();
    }

    public int size(){
        return orders.size();
    }

    public void addOrder(String orderID,  order order) throws wrongTaskFormatException {
        if(orders.put(orderID, order)!= null) throw new wrongTaskFormatException("such order already exists");
        orderIDs.add(orderID);
    }

    public void writeBestPathFor(graph graph, order order, String restaurantStreetID) throws wrongTaskFormatException {
        order.writePathAndTime(graph, restaurantStreetID);
    }

    public order getOrder(String orderID){
        return (order) orders.get(orderID);
    }

    public order getOrder(int orderNumber){
        return (order) orders.get(orderIDs.get(orderNumber));
    }
}
