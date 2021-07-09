package com.company.model.graph;

import java.util.*;

public class Vertex implements Comparable<Vertex> {
    private final String name;
    private List<Edge> edges;
    private boolean visited;
    private Vertex previousVertex;
    private double minDistance = Double.MAX_VALUE;

    private final double lat;
    private final double lon;

    /**
     * это должно отвратительно работать, если будет больше 1 графа
     */
    //static PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();

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

    public void computeMinPaths(){
        setMinDistance(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(this);

        while (!priorityQueue.isEmpty()) {
            Vertex vertex = priorityQueue.poll();

            for (Edge edge : vertex.getEdges()) {
                Vertex v = edge.getTargetVertex();
                double weight = edge.getWeight();
                double minDistance = vertex.getMinDistance() + weight;

                if (minDistance < v.getMinDistance()) {
                    priorityQueue.remove(vertex);
                    v.setPreviousVertex(vertex);
                    v.setMinDistance(minDistance);
                    priorityQueue.add(v);
                }
            }
        }
    }

    //todo: 2
    public Iterator<Vertex> prevIterator(Vertex target){
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

    /**
     * никогда не вызывать этот метод
     * ломает previousVertex
     */
    public void printGraph(){
        for(Edge edge: this.getEdges()){
            if(edge.getTargetVertex() == previousVertex) continue;
            System.out.println(
                        edge.getStartVertex().getName() + " --" + edge.getWeight() + "-> " + edge.getTargetVertex().getName()
                );
            edge.getTargetVertex().setPreviousVertex(this);
            edge.getTargetVertex().printGraph();
        }
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
