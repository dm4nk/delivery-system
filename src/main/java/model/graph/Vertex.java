package model.graph;

import java.util.*;

/**
 * Represents vertex in graph
 */
public class Vertex implements Comparable<Vertex> {
    private final String name;
    private List<Edge> edges;
    private boolean visited;
    private Vertex previousVertex;
    private double minDistance = Double.MAX_VALUE;
    private final double lat;
    private final double lon;

    public Vertex(String name, double lon, double lat) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.lat = lat;
        this.lon = lon;
    }

    public Vertex(String name) {
        this(name, 180, 180);
    }

    /**
     * Sets certain fields of vertex to default
     */
    public void validate() {
        minDistance = Double.MAX_VALUE;
        previousVertex = null;
        visited = false;
    }

    /**
     * Adds an edge to this vertex
     *
     * @param edge between this vertex as source vertex and another vertex
     */
    public void addNeighbour(Edge edge) {
        this.edges.add(edge);
    }

    /**
     * @return list of all edges that go from this vertex
     */
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

    /**
     * used after Dijksta algorithm to get shortest path
     */
    public Vertex getPreviousVertex() {
        return previousVertex;
    }

    /**
     * Used after Dijksta algorithm to get shortest path
     */
    public void setPreviousVertex(Vertex previousVertex) {
        this.previousVertex = previousVertex;
    }

    /**
     * Used after Dijksta algorithm to get shortest path
     */
    public double getMinDistance() {
        return minDistance;
    }

    /**
     * Used after Dijksta algorithm to get shortest path
     */
    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    /**
     * Euclid length between 2 vertices
     */
    public double getDistanceTo(Vertex vertex) {
        return Math.sqrt(Math.pow((lon - vertex.getLon()), 2) + Math.pow((lat - vertex.getLat()), 2));
    }

    /**
     * Computes min paths from this vertex to all others in graph
     */
    public void computeMinPaths() {
        setMinDistance(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(this);

        double minDistance;
        Vertex vertex;
        Vertex v;
        while (!priorityQueue.isEmpty()) {
            vertex = priorityQueue.poll();

            for (Edge edge : vertex.getEdges()) {
                v = edge.getTargetVertex();
                minDistance = vertex.getMinDistance() + edge.getWeight();

                if (minDistance < v.getMinDistance()) {
                    priorityQueue.remove(vertex);
                    v.setPreviousVertex(vertex);
                    v.setMinDistance(minDistance);
                    priorityQueue.add(v);
                }
            }
        }
    }

    public Iterator<Vertex> prevIterator(Vertex target) {
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
    public String toString() {
        return name;
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
}
