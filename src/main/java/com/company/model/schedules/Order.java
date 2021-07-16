package com.company.model.schedules;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Vertex;
import com.company.model.graph.Graph;
import com.company.algorithms.Dijkstra;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

public class Order implements Serializable {
    private final String id;
    private Date dispatchTime;
    private Date arrivalTime;
    private final double lat;
    private final double lon;

    private Vertex street = null;

    public Order(String id, Date dispatchTime, double lon, double lat){
        this.id = id;
        this.dispatchTime = dispatchTime;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public boolean isStreetSat(){
        return street != null;
    }

    public Vertex getStreet() {
        return street;
    }

    /**
     * выставляет нужно значение поля street
     */
    public void setStreet(Graph graph){
        street = calculateNearestVertex(graph);
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

    public Vertex calculateNearestVertex(Graph graph){
        return Dijkstra.calculateNearestVertexFromLatLon(graph, lon, lat);
    }

    public List<Vertex> writeBestPath(Graph graph, Vertex fromVertex) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(this, fromVertex);
    }

    public List<Vertex> writeBestPath(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return graph.writeBestPath(this, fromVertices);
    }

    public List<Vertex> write2PathsAndTime(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        return graph.write2PathsAndTime(this, fromVertices);
    }
}