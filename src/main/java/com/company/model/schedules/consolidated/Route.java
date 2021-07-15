package com.company.model.schedules.consolidated;

import com.company.model.graph.Vertex;
import com.company.model.schedules.Order;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Order> route;

    Route(){
        route = new ArrayList<>();
    }

    public boolean add(Order order){
        if(route.size() == 0 ){
            route.add(order);
            return true;
        }
        if(route.size() == 3) return false;
        if(route.get(route.size()-1).getStreet().getDistanceTo(order.getStreet()) > 0.005) return false;

        route.add(order);
        return true;
    }

    public void writeRoute(){
        route.forEach(v ->
                System.out.print(v.getId()+ " -> ")
        );
    }
}
