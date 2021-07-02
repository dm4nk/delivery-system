package com.company.model.schedules;

import com.company.model.Exceptions.wrongTaskFormatException;
import com.company.model.graph.graph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void addOrder(String orderID,  order order){
        orders.put(orderID, order);
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
