package com.company.model.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex implements Comparable<Vertex>{
    private final String name;
    private List<Edge> edges;
    private boolean visited;
    private Vertex previousVertex;
    private double minDistance = Double.MAX_VALUE;
    private final double lat;
    private final double lon;

    public Vertex(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.lat = -1;
        this.lon = -1;
    }

    public Vertex(String name, double lat, double lon) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.lat = lat;
        this.lon = lon;
    }

    public void addNeighbour(Edge edge) {
        this.edges.add(edge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertex getPreviousVertex() {
        return previousVertex;
    }

    public void setPreviousVertex(Vertex previousVertex) {
        this.previousVertex = previousVertex;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public String getName() {
        return name;
    }

    public double getLat(){
        return lat;
    }

    public double getLon(){
        return lon;
    }

    @Override
    public int compareTo(Vertex otherVertex) {
        return Double.compare(this.minDistance, otherVertex.minDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return visited == vertex.visited &&
                Double.compare(vertex.minDistance, minDistance) == 0 &&
                Double.compare(vertex.lat, lat) == 0 &&
                Double.compare(vertex.lon, lon) == 0 &&
                Objects.equals(name, vertex.name) &&
                Objects.equals(edges, vertex.edges) &&
                Objects.equals(previousVertex, vertex.previousVertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, edges, lat, lon);
    }
}
