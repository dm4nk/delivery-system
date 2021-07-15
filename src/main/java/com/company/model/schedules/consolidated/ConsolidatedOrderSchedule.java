package com.company.model.schedules.consolidated;

import com.company.model.graph.Graph;
import com.company.model.schedules.Order;
import com.company.model.schedules.OrdersSchedule;

import java.util.ArrayList;
import java.util.List;

public class ConsolidatedOrderSchedule {
    private List<Route> routes;

    public ConsolidatedOrderSchedule(OrdersSchedule ordersSchedule){
        routes = new ArrayList<>();
        for(int i = 0; i < ordersSchedule.size(); ++i) {
            addAsConsolidated(ordersSchedule.getOrder(i));
        }
    }

    public ConsolidatedOrderSchedule(){
        routes = new ArrayList<>();
    }

    public void addAsConsolidated(Order order){
        boolean isAdded = false;
        order.setStreet(Graph.getInstance());//////////ujdyj
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

    public void writeOrders(){
        routes.forEach(r -> {
            r.writeRoute();
            System.out.println();
        });
    }
}
