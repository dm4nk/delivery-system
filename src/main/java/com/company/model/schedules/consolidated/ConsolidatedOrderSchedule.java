package com.company.model.schedules.consolidated;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.company.model.schedules.Schedule;
import com.company.model.schedules.Order;

import java.text.ParseException;
import java.util.*;

public class ConsolidatedOrderSchedule implements Schedule {
    private List<Route> routes;

    public ConsolidatedOrderSchedule(){
        routes = new LinkedList<>();
    }

    //todo: отдельный консолидатор

    //todo: класс, который создает рут

    //find root. get root галочка
    //покрасивее оформить addOrder() галочка
    //фабрика, которая создает consOrders в зависимости от параметра галочка

    @Override
    public void addOrder(Order order){
        //order.setStreet(Graph.getInstance());//todo: перенести это в конструктор ордеров и переписать всю прогу под это
        for(Route r : routes)
            if(r.add(order)) return;

        Route tmp = new Route();
        tmp.add(order);
        routes.add(tmp);
    }

    @Override
    public void writePaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        writeConsolidatedPaths(graph, fromVertices);
    }

    public Route getRoute(int index){
        return routes.get(index);
    }

    public Route remove(int index){
        return routes.remove(index);
    }

    public void writeOrders(){
        routes.forEach(r -> {
            r.writeRoute();
            System.out.println();
        });
    }

    public List<List<Vertex>> writeConsolidatedPaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        List<List<Vertex>> consolidatedPaths = new ArrayList<>();

        for(Route r: routes){
            System.out.println("\nRoute: ");
            consolidatedPaths.add(r.writeBestPath(graph, fromVertices));
        }

        routes.removeAll(routes);

        return consolidatedPaths;
    }
}
