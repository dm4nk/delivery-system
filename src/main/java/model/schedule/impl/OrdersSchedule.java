package model.schedule.impl;

import exceptions.WrongOrderFormatException;
import model.graph.Graph;
import model.graph.Vertex;
import model.schedule.Order;
import model.schedule.Schedule;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * Represents consistent schedule
 */
public class OrdersSchedule implements Schedule, Serializable {
    final Map<String, Order> orders;
    final List<String> orderIDs;

    private OrdersSchedule(Map<String, Order> orders, LinkedList<String> orderIDs) {
        this.orders = orders;
        this.orderIDs = orderIDs;
    }

    public static OrdersSchedule create(Map<String, Order> orders, LinkedList<String> orderIDs) {
        return new OrdersSchedule(orders, orderIDs);
    }

    public static OrdersSchedule create() {
        return new OrdersSchedule(new HashMap<>(), new LinkedList<>());
    }

    public int size() {
        return orders.size();
    }

    /**
     * Writes best path for this order in console
     *
     * @param graph      graph to work with
     * @param order      which order to execute
     * @param fromVertex street to deliver from
     * @return inverted best path
     * @throws WrongOrderFormatException if destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> writeBestPathFor(Graph graph, Order order, Vertex fromVertex) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(order, fromVertex);
    }

    /**
     * Writes best path for this order in console
     *
     * @param graph        graph to work with
     * @param order        which order to execute
     * @param fromVertices streets that ve can go from
     * @return inverted best path
     * @throws WrongOrderFormatException if list is empty or destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> writeBestPathFor(Graph graph, Order order, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(order, fromVertices);
    }

    /**
     * Writes 2 best paths for this order in console
     *
     * @param graph      graph to work with
     * @param order      which order to execute
     * @param fromVertex street to go from
     * @return inverted best alternative path, or inverted shortest path, if there is no alternative
     * @throws WrongOrderFormatException if list is empty or destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> write2BestPathsFor(Graph graph, Order order, Vertex fromVertex) throws WrongOrderFormatException, ParseException {
        List<Vertex> vert = new ArrayList<>(1);
        vert.add(fromVertex);
        return graph.write2PathsAndTime(order, vert);
    }

    /**
     * Writes 2 best paths for this order in console
     *
     * @param graph        graph to work with
     * @param order        which order to execute
     * @param fromVertices streets that ve can go from
     * @return inverted best alternative path, or inverted shortest path, if there is no alternative
     * @throws WrongOrderFormatException if list is empty or destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> write2BestPathsFor(Graph graph, Order order, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return graph.write2PathsAndTime(order, fromVertices);
    }

    public Order getOrder(String orderID) {
        return orders.get(orderID);
    }

    public Order getOrder(int index) {
        return orders.get(orderIDs.get(index));
    }

    /**
     * @param graph graph to work with
     * @param order order to add
     * @throws WrongOrderFormatException if such order already exists
     */
    @Override
    public void addOrder(Graph graph, Order order) throws WrongOrderFormatException {
        order.setNearestVertex(graph);
        if (orders.put(order.getId(), order) != null) throw new WrongOrderFormatException("such Order already exists");
        orderIDs.add(order.getId());
    }

    /**
     * Writes all paths of this schedule to console according to its type
     *
     * @param graph        graph to work with
     * @param fromVertices vertices we can go from
     * @throws WrongOrderFormatException if there is no such vertices in graph
     */
    @Override
    public void writePaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        for (Order o : orders.values()) {
            System.out.println("\nOrder: ");
            write2BestPathsFor(graph, o, fromVertices);
        }
    }
}
