package com.company.model.graph;

import java.util.*;

public class Vertex implements Comparable<Vertex> {
    private final String name;
    private List<Edge> edges;//TODO: через это тоже можно сделать рекуррентный вывод графа
    private boolean visited;
    private Vertex previousVertex;//TODO: через эту штуку можно сделать рекурретный вывод графа в дейкстру
    private double minDistance = Double.MAX_VALUE;

    private final double lat;
    private final double lon;

    //это должно отвратительно работать, если юудет больше 1 графа
    static PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();

    public Vertex(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.lat = 180;
        this.lon = 180;
    }

    public Vertex(String name, double lon, double lat) {
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

    private void poll(){
        double minDistance;

        for (Edge edge : getEdges()) {
            Vertex v = edge.getTargetVertex();
            minDistance = getMinDistance() + edge.getWeight();

            if (minDistance < v.getMinDistance()) {
                priorityQueue.remove(this);
                v.setPreviousVertex(this);
                v.setMinDistance(minDistance);
                priorityQueue.add(v);
            }
        }
    }

    public void computeMinPaths(){
        setMinDistance(0);
        priorityQueue.add(this);
        while (!priorityQueue.isEmpty()) {
            priorityQueue.poll().poll();
        }
    }

    public Iterator<Vertex> iterator(Vertex target){
        return new Iterator<Vertex>() {
            Vertex next = target;
            @Override
            public boolean hasNext() {
                return next.previousVertex != null;
            }

            @Override
            public Vertex next() {
                next = next.previousVertex;
                return next;
            }
        };
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
