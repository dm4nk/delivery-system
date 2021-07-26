package model.schedule.impl;

import exceptions.WrongOrderFormatException;
import model.graph.Graph;
import model.graph.Vertex;
import model.schedule.Order;
import model.schedule.Route;
import model.schedule.Schedule;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class ConsolidatedOrderSchedule implements Schedule {
    private final List<Route> routes;
    private static final double MAX_DISTANCE_FOR_CONSOLIDATION = 0.05;//5 km

    private ConsolidatedOrderSchedule() {
        routes = new LinkedList<>();
    }

    private ConsolidatedOrderSchedule(Graph graph, OrdersSchedule ordersSchedule) throws WrongOrderFormatException {
        routes = new LinkedList<>();
        for (int i = 0; i < ordersSchedule.size(); ++i)
            addOrder(graph, ordersSchedule.getOrder(i));
    }

    public static ConsolidatedOrderSchedule create() {
        return new ConsolidatedOrderSchedule();
    }

    public static ConsolidatedOrderSchedule createAndConsolidate(Graph graph, OrdersSchedule ordersSchedule) throws WrongOrderFormatException {
        return new ConsolidatedOrderSchedule(graph, ordersSchedule);
    }

    /**
     * @return root with nearest last order
     * or null if nearest order is more than 5km away
     */
    private Route findMostSuitableRoot(Order order) {
        double minDistance = Double.MAX_VALUE;
        Route minDistanceRoot = null;
        for (Route r : routes) {
            if (r.getDistanceToLastOrder(order) < minDistance) {
                minDistance = r.getDistanceToLastOrder(order);
                minDistanceRoot = r;
            }
        }

        if (minDistance > MAX_DISTANCE_FOR_CONSOLIDATION) return null;
        return minDistanceRoot;
    }

    public Route getRoute(int index) {
        return routes.get(index);
    }

    public Route remove(int index) {
        return routes.remove(index);
    }

    /**
     * Write all orders to console
     */
    public void writeOrders() {
        routes.forEach(r -> {
            r.writeRoute();
            System.out.println();
        });
    }

    /**
     * Writes best path for this route in console as consolidated
     *
     * @param graph        graph to work with
     * @param route        which route to execute
     * @param fromVertices streets we can go from
     * @return inverted best path
     * @throws WrongOrderFormatException if destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> writeConsolidatedPath(Graph graph, Route route, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return route.writeBestPath(graph, fromVertices);
    }

    /**
     * @param graph graph to work with
     * @param order order to add
     * @throws WrongOrderFormatException if such order already exists
     */
    @Override
    public void addOrder(Graph graph, Order order) throws WrongOrderFormatException {
        order.setNearestVertex(graph);
        Route minDistanceRoot = findMostSuitableRoot(order);
        if (minDistanceRoot == null || !minDistanceRoot.add(order)) routes.add(Route.create(order));
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
        for (Route r : routes) {
            System.out.println("\nRoute: ");
            writeConsolidatedPath(graph, r, fromVertices);
        }
    }
}
