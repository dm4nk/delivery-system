package model.schedule;

import exceptions.WrongOrderFormatException;
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
public class Order implements Serializable {
    private final String id;
    private Date dispatchTime;
    private Date arrivalTime;
    private final double lat;
    private final double lon;
    private Vertex street = null;

    private static final double MILLISECONDS_IN_MINUTE = 60_000d;

    public Order(String id, Date dispatchTime, double lon, double lat) {
        this.id = id;
        this.dispatchTime = dispatchTime;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public boolean isStreetSat() {
        return street != null;
    }

    public Vertex getVertex() {
        return street;
    }

    /**
     * sets a street (vertex), which order was made from
     */
    public void setNearestVertex(Graph graph) throws WrongOrderFormatException {
        street = calculateNearestVertex(graph);
        if (street == null)
            throw new WrongOrderFormatException("Order is more than 200 meters away from delivery zone");
    }

    /**
     * @return time required for delivering this order
     */
    public double timeRequired() {
        return (arrivalTime.getTime() - dispatchTime.getTime()) / MILLISECONDS_IN_MINUTE;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
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
        return Dijkstra.calculateNearestVertexFromLatLon(graph, lon, lat);
    }

    /**
     * writes best path for this order in console
     *
     * @param fromVertex street to deliver from
     * @return inverted best path
     * @throws WrongOrderFormatException if destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> writeBestPath(Graph graph, Vertex fromVertex) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(this, fromVertex);
    }

    /**
     * writes best path for this order in console
     *
     * @param fromVertices streets that ve can go from
     * @return inverted best path
     * @throws WrongOrderFormatException if list is empty or destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> writeBestPath(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(this, fromVertices);
    }

    /**
     * writes 2 best paths for this order in console
     *
     * @param fromVertices streets that ve can go from
     * @return inverted best alternative path, or inverted shortest path, if there is no alternative
     * @throws WrongOrderFormatException if list is empty or destination is more than 200 meters away from delivery zone
     */
    public List<Vertex> write2PathsAndTime(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return graph.write2PathsAndTime(this, fromVertices);
    }
}