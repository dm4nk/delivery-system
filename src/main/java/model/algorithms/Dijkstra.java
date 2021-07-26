package model.algorithms;

import exceptions.WrongOrderFormatException;
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
    public static void computePath(Vertex sourceVertex) throws WrongOrderFormatException {
        if (sourceVertex == null) throw new WrongOrderFormatException("no such point");

        sourceVertex.computeMinPaths();
    }

    /**
     * Before usage, must call computePath() with dispatch vertex as argument
     *
     * @param targetVertex - arrival vertex
     * @return inverted shortest path
     */
    public static List<Vertex> getShortestPathTo(Vertex targetVertex) throws WrongOrderFormatException {

        if (targetVertex == null) throw new WrongOrderFormatException("no such point");

        List<Vertex> path = new ArrayList<>();

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
    public static Vertex calculateNearestVertexFromLatLon(Graph graph, double lon, double lat) {
        String nearestVertexName = graph.getTree().getNearestVertexName(lon, lat);
        return graph.getVertices().get(nearestVertexName);
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
