package model.algorithms;

import lombok.NonNull;
import model.graph.Graph;
import model.graph.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Dijkstra {

    private Dijkstra() {
    }

    /**
     * Must be used before getShortestPathTo()
     *
     * @param sourceVertex - dispatch vertex
     */
    public static void computePath(@NonNull Vertex sourceVertex) {
        sourceVertex.computeMinPaths();
    }

    /**
     * Before usage, must call computePath() with dispatch vertex as argument
     *
     * @param targetVertex - arrival vertex
     * @return inverted shortest path
     */
    public static List<Vertex> getShortestPathTo(@NonNull Vertex targetVertex) {
        List<model.graph.Vertex> path = new ArrayList<>();

        path.add(targetVertex);

        Iterator<Vertex> it = targetVertex.prevIterator(targetVertex);
        while (it.hasNext()) {
            path.add(it.next());
        }

        return path;
    }

    /**
     * Nearest vertex in graph to this point
     *
     * @return null, if point is 200 away from map
     */
    public static Vertex calculateNearestVertexFromLatLon(Graph graph, double lat, double lon) {
        Long nearestVertexId = graph.getTree().getNearestVertexId(lat, lon);
        return nearestVertexId == null ? null : graph.getVertex(nearestVertexId);
    }

    /**
     * Prints vertex list in console
     *
     * @param vertices - result of hetShortestPathTo()
     */
    public static void printVertexListAsPath(List<Vertex> vertices) {
        if (vertices == null) {
            System.out.println("no such path: no routes to destination point");
            return;
        }
        ListIterator<Vertex> it = vertices.listIterator(vertices.size() - 1);

        System.out.print(vertices.get(vertices.size() - 1));
        while (it.hasPrevious())
            System.out.print(" -> " + it.previous().toString());

        System.out.println();
    }
}
