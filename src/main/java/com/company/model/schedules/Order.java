package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Vertex;
import com.company.model.graph.Graph;
import com.company.algorithms.Dijkstra;
import java.io.Serializable;
import java.util.*;

public class Order implements Serializable {
    private final String id;
    private final Date date;
    private final double lat;
    private final double lon;

    private Vertex street = null;

    public Order(String id, Date date, double lon, double lat){
        this.id = id;
        this.date = date;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public Vertex getStreet() {
        return street;
    }

    public void setStreet(Graph graph){
        street = calculateNearestVertex(graph);
    }

    public Date getDate() {
        return date;
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

    public List<Vertex> writeBestPath(Graph graph, Vertex fromVertex) throws WrongTaskFormatException {
        return graph.writeBestPath(this, fromVertex);
    }

    public List<Vertex> writeBestPath(Graph graph, List<Vertex> fromVertices) throws WrongTaskFormatException {
        return graph.writeBestPath(this, fromVertices);
    }

    public List<Vertex> write2PathsAndTime(Graph graph, List<Vertex> fromVertices) throws WrongTaskFormatException {
        return graph.write2PathsAndTime(this, fromVertices);
    }
}