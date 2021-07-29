package model.schedule;

import exceptions.WrongOrderFormatException;
import model.graph.Graph;
import model.graph.Vertex;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a single route for courier, containing 3 orders as maximum
 */

public class Route {
    private final List<Order> route;

    private Route(Order order) {
        route = new LinkedList<>();
        route.add(order);
    }

    public static Route create(Order order) {
        return new Route(order);
    }

    /**
     * @return true, if you can add order to this Route (less then 3 orders)
     */
    public boolean add(Order order) {
        if (route.size() == 3) return false;

        route.add(order);
        return true;
    }

    /**
     * @return distance from last order of this route
     */
    public double getDistanceToLastOrder(Order order) {
        return route.get(route.size() - 1).getDistanceTo(order);
    }

    public Order getOrder(int i) {
        return route.get(i);
    }

    public int size() {
        return route.size();
    }

    /**
     * Writes all routes in console
     */
    public void writeRoute() {
        route.forEach(v ->
                System.out.print(v.getId() + " -> ")
        );
    }

    /**
     * Writes best path for this order in console
     *
     * @param fromVertices streets that ve can go from
     * @return inverted best path
     */
    public List<Vertex> writeBestPath(Graph graph, List<Vertex> fromVertices) throws ParseException {
        List<Vertex> path = new ArrayList<>(
                route.get(0).writeBestPath(graph, fromVertices)
        );

        Vertex fromStreet = route.get(0).getVertex();

        for (int i = 1; i < route.size(); ++i) {
            System.out.println();
            route.get(i).setDispatchTime(route.get(i - 1).getArrivalTime());
            path.addAll(route.get(i).writeBestPath(graph, fromStreet));
            fromStreet = route.get(i).getVertex();
        }

        return path;
    }
}
