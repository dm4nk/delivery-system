package model.graph;

import exceptions.WrongGraphFormatException;
import lombok.Getter;
import lombok.NonNull;
import model.algorithms.Dijkstra;
import model.algorithms.GraphReader;
import model.algorithms.GraphWriter;
import model.dto.DTO;
import model.schedule.Order;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Graph {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
    private final static int MILLISECONDS_IN_MINUTE = 60_000;

    private static Graph singleInstance = null;
    @Getter
    private final Map<Long, Vertex> vertices;
    @Getter
    private final RTreeAdapter tree;

    protected Graph() {
        vertices = new HashMap<>();
        tree = RTreeAdapter.create();
        formatter.setLenient(false);
    }

    /**
     * made to implement singleton pattern
     *
     * @return instance of a graph
     */
    public static Graph getInstance() {
        if (singleInstance == null) {
            singleInstance = new Graph();
        }
        return singleInstance;
    }

    public int size() {
        return vertices.size();
    }

    /**
     * Adds a vertex to graph with no edges
     *
     * @throws WrongGraphFormatException if vertex with such name already exists
     */
    public void addVertex(@NonNull Vertex vertex) throws WrongGraphFormatException {
        if (vertices.put(vertex.getId(), vertex) != null)
            throw new WrongGraphFormatException("such point already exists");
        tree.add(vertex.getId(), vertex.getLat(), vertex.getLon());
    }

    public void addAllVertices(@NonNull List<Vertex> vertexList) throws WrongGraphFormatException {
        for (Vertex v : vertexList) {
            if (vertices.put(v.getId(), v) != null) throw new WrongGraphFormatException("such point already exists");
            tree.add(v.getId(), v.getLat(), v.getLon());
        }
    }

    public Vertex getVertex(long id) {
        return vertices.getOrDefault(id, null);
    }

    /**
     * @param id id of vertex
     * @throws NoSuchElementException if graph does not contain vertex with such id
     */
    public void removeVertex(long id) {
        if (!vertices.containsKey(id)) throw new NoSuchElementException("no vertex with such id");
        Vertex removed = vertices.get(id);
        vertices.values().forEach(v -> v.getEdges().removeIf(e -> e.getTargetVertex() == removed));
        tree.remove(id, removed.getLon(), removed.getLat());
        vertices.remove(id);
    }

    /**
     * makes edge between 2 vertices in graph
     */
    public void addEdge(@NonNull Edge edge) {
        edge.getStartVertex().addNeighbour(edge);
    }

    /**
     * makes edges between 2 vertices in graph
     */
    public void addAllEdges(@NonNull List<Edge> edgeList) {
        edgeList.forEach(e -> e.getStartVertex().addNeighbour(e));
    }

    /**
     * removes edge between 2 vertices in graph
     *
     * @param sourceVertex dispatch vertex
     * @param targetVertex arrival vertex
     * @throws NoSuchElementException if there is no vertices with such names
     */
    public void removeEdge(long sourceVertex, long targetVertex) {
        Vertex target = vertices.getOrDefault(targetVertex, null);
        Vertex source = vertices.getOrDefault(sourceVertex, null);
        if (target == null || source == null) throw new NoSuchElementException("no vertex with such id");
        source.getEdges().removeIf(removed -> removed.getTargetVertex() == target);
    }

    /**
     * makes graph from matrix n*n.
     * <p>
     * if there is no path between 2 vertices, write 0 in matrix
     *
     * @param file .txt file with matrix
     * @throws IOException               error while reading file
     * @throws WrongGraphFormatException matrix is not n*n, contains characters other than positive doubles
     */
    public void readGraphFromTXT(@NonNull File file) throws IOException, WrongGraphFormatException {
        GraphReader.readGraph(file, this);
    }

    /**
     * reads nodes and edges to this graph
     *
     * @param vertices list of vertex dtos
     * @param edges    list of edge dtos
     * @throws WrongGraphFormatException if lists contain duplicates
     */
    public void readGraphFromDTOs(@NonNull List<DTO.vertex> vertices, @NonNull List<DTO.edge> edges) throws WrongGraphFormatException {
        GraphReader.readGraph(vertices, edges, this);
    }

    /**
     * writes graph into console
     */
    public void writeGraph() {
        GraphWriter.writeGraph(this);
    }

    /**
     * must use after any operations with path calculating from package algorithms
     * returns fields in vertices to default state
     */
    public void validate() {
        for (Vertex v : vertices.values())
            v.validate();
    }

    private String parseDateToString(Date date) {
        return formatter.format(date);
    }

    private Date parseMillisecondsToDate(long mills) throws ParseException {
        return formatter.parse(formatter.format(mills));
    }

    /**
     * Calculates nearest vertex to toVertex from List
     *
     * @param toVertex     dispatch vertex
     * @param fromVertices arrival vertex
     * @return nearest vertex
     */
    private Vertex getBestVertexToStartFrom(@NonNull Vertex toVertex, @NonNull List<Vertex> fromVertices) {
        long from = Long.MIN_VALUE;
        double minPath = Double.MAX_VALUE;

        for (Vertex tmp : fromVertices) {
            Dijkstra.computePath(tmp);
            if (toVertex.getMinDistance() <= minPath) {
                from = tmp.getId();
                minPath = toVertex.getMinDistance();
            }
            validate();
        }

        return vertices.getOrDefault(from, null);
    }

    /**
     * Writes best path for this order in console
     *
     * @param order      which order to execute
     * @param fromVertex street to go from
     * @return inverted best path
     */
    public List<Vertex> writeBestPath(@NonNull Order order, @NonNull Vertex fromVertex) throws ParseException {
        Vertex toVertex = order.getVertex();

        Dijkstra.computePath(fromVertex);

        System.out.println(order.getId() + ": ");

        if (toVertex.getMinDistance() == Double.MAX_VALUE) {
            System.out.println("No such path");
            return null;
        }

        System.out.println("Dispatch time: " + parseDateToString(order.getDispatchTime()));
        System.out.print("Path: ");

        List<Vertex> path = Dijkstra.getShortestPathTo(toVertex);
        Dijkstra.printVertexListAsPath(path);

        System.out.println("Time required: " + toVertex.getMinDistance());

        order.setArrivalTime(parseMillisecondsToDate(
                order.getDispatchTime().getTime() + (long) toVertex.getMinDistance() * MILLISECONDS_IN_MINUTE)
        );
        System.out.println("Approximate delivery time: " + parseDateToString(order.getArrivalTime()));

        validate();
        return path;
    }

    /**
     * Writes best path for this order in console
     *
     * @param order        which order to execute
     * @param fromVertices streets that we can go from
     * @return inverted best path
     */
    public List<Vertex> writeBestPath(@NonNull Order order, @NonNull List<Vertex> fromVertices) throws ParseException {
        Vertex toVertex = order.getVertex();
        Vertex fromVertex = getBestVertexToStartFrom(toVertex, fromVertices);
        if (fromVertex == null) {
            System.out.println("No such path");
            return null;
        }
        return writeBestPath(order, fromVertex);
    }

    /**
     * Writes 2 best paths for this order in console
     *
     * @param order        which order to execute
     * @param fromVertices streets that we can go from
     * @return inverted best alternative path, or inverted shortest path, if there is no alternative
     */
    public List<Vertex> write2PathsAndTime(@NonNull Order order, @NonNull List<Vertex> fromVertices) throws ParseException {
        List<Vertex> firstPath = writeBestPath(order, fromVertices);
        if (firstPath == null || firstPath.size() <= 10) return firstPath;

        List<Edge> temp;
        List<Vertex> secondPath;
        for (int i = 1; i < firstPath.size(); ++i) {
            System.out.println();

            temp = firstPath.get(i).getEdges();
            firstPath.get(i).setEdges(new ArrayList<>());
            secondPath = writeBestPath(order, fromVertices);
            firstPath.get(i).setEdges(temp);

            if (secondPath != null) return secondPath;
        }
        return firstPath;
    }

    /**
     * saves a .png of r tree to target/tree.png
     */
    public void visualize() {
        tree.visualize("target/tree.png");
    }
}