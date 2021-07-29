package model.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Represents vertex in graph
 */
@Data(staticConstructor = "create")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vertex implements Comparable<Vertex> {
    @EqualsAndHashCode.Include
    private final long id;
    @EqualsAndHashCode.Include
    private final double lat;
    @EqualsAndHashCode.Include
    private final double lon;
    private List<Edge> edges = new ArrayList<>();
    private boolean visited;
    /**
     * Used after Dijksta algorithm to get shortest path
     */
    private Vertex previousVertex;
    /**
     * Used after Dijksta algorithm to get shortest path
     */
    private double minDistance = Double.MAX_VALUE;

    public static Vertex create(long id) {
        return new Vertex(id, 180, 180);
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
    public void addNeighbour(@NonNull Edge edge) {
        this.edges.add(edge);
    }

    /**
     * Euclid length between 2 vertices
     */
    public double getDistanceTo(@NonNull Vertex vertex) {
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

    public Iterator<Vertex> prevIterator(@NonNull Vertex target) {
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
        return Long.toString(id);
    }

    @Override
    public int compareTo(@NonNull Vertex otherVertex) {
        return Double.compare(this.minDistance, otherVertex.minDistance);
    }
}