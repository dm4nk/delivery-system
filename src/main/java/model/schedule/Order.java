package model.schedule;

import exceptions.WrongOrderFormatException;
import lombok.Data;
import lombok.NonNull;
import model.algorithms.Dijkstra;
import model.graph.Graph;
import model.graph.Vertex;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Represents single order.
 */
@Data(staticConstructor = "create")
public class Order implements Serializable {
    private static final double MILLISECONDS_IN_MINUTE = 60_000d;
    private final String id;
    private final double lat;
    private final double lon;
    @NonNull
    private Date dispatchTime;
    private Date arrivalTime;
    private Vertex vertex = null;

    /**
     * sets a street (vertex), which order was made from
     */
    public void setNearestVertex(Graph graph) throws WrongOrderFormatException {
        vertex = calculateNearestVertex(graph);
        if (vertex == null)
            throw new WrongOrderFormatException("Order is more than 200 meters away from delivery zone");
    }

    /**
     * @return time required for delivering this order
     */
    public double timeRequired() {
        return (arrivalTime.getTime() - dispatchTime.getTime()) / MILLISECONDS_IN_MINUTE;
    }

    /**
     * @return Euclid distance to order
     */
    public double getDistanceTo(Order order) {
        return Math.sqrt(Math.pow((lon - order.getLon()), 2) + Math.pow((lat - order.getLat()), 2));
    }

    /**
     * @param graph graph to work with
     * @return nearest street to this order coordinates
     */
    private Vertex calculateNearestVertex(Graph graph) {
        return Dijkstra.calculateNearestVertexFromLatLon(graph, lat, lon);
    }

    /**
     * writes best path for this order in console
     *
     * @param fromVertex street to deliver from
     * @return inverted best path
     */
    public List<Vertex> writeBestPath(Graph graph, Vertex fromVertex) throws ParseException {
        return graph.writeBestPath(this, fromVertex);
    }

    /**
     * writes best path for this order in console
     *
     * @param fromVertices streets that ve can go from
     * @return inverted best path
     */
    public List<Vertex> writeBestPath(Graph graph, List<Vertex> fromVertices) throws ParseException {
        return graph.writeBestPath(this, fromVertices);
    }

    /**
     * writes 2 best paths for this order in console
     *
     * @param fromVertices streets that ve can go from
     * @return inverted best alternative path, or inverted shortest path, if there is no alternative
     */
    public List<Vertex> write2PathsAndTime(Graph graph, List<Vertex> fromVertices) throws ParseException {
        return graph.write2PathsAndTime(this, fromVertices);
    }
}