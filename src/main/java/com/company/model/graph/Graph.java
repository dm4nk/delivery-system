package com.company.model.graph;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongOrderFormatException;
import com.company.algorithms.GraphWriter;
import com.company.model.schedules.Order;
import com.company.algorithms.GraphReader;
import com.company.algorithms.Dijkstra;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Graph {
    private final Map<String, Vertex> vertices;
    private final RTreeAdapter tree;
    private final static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
    private final static int MILLISECONDS_IN_MINUTE = 60_000;

    private static Graph singleInstance = null;

    protected Graph(){
        vertices = new HashMap<>();
        tree = RTreeAdapter.create();
        formatter.setLenient(false);
    }

    public static Graph getInstance(){
        if(singleInstance == null){
            singleInstance = new Graph();
        }
        return singleInstance;
    }

    public  int size(){
        return vertices.size();
    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public RTreeAdapter getTree(){
        return tree;
    }

    public void addVertex(String name) throws WrongGraphFormatException {
        if(vertices.put(name, new Vertex(name)) != null) throw new WrongGraphFormatException("such point already exists");
        tree.add(name, 180, 180);
    }

    public void addVertex(String name, double lon, double lat) throws WrongGraphFormatException {
        if(vertices.put(name, new Vertex(name, lon, lat)) != null) throw new WrongGraphFormatException("such point already exists");
        tree.add(name, lon, lat);
    }

    public Vertex getVertex(String id){
        return vertices.getOrDefault(id, null);
    }

    public void removeVertex(String name){
        if(!vertices.containsKey(name)) throw new NoSuchElementException("no vertex with such name");
        Vertex removed = vertices.get(name);
        vertices.values().forEach(v -> v.getEdges().removeIf(e -> e.getTargetVertex() == removed));
        tree.remove(name, removed.getLon(),removed.getLat());
        vertices.remove(name);
    }

    public void addEdge(double weight, String sourceVertex, String targetVertex) throws WrongGraphFormatException {
        if(sourceVertex.equals(targetVertex)) throw new WrongGraphFormatException("source vertex equals to target vertex");
        if(weight<0)throw  new WrongGraphFormatException("negative weight");
        try {
            vertices.get(sourceVertex).addNeighbour(
                    new Edge(
                            weight,
                            vertices.get(sourceVertex),
                            vertices.get(targetVertex)
                    )
            );
        }
        catch (NullPointerException e){
            throw new WrongGraphFormatException("no such points");
        }
    }

    public void removeEdge(String sourceVertex, String targetVertex) {
        Vertex target = vertices.getOrDefault(targetVertex, null);
        Vertex source = vertices.getOrDefault(sourceVertex, null);
        if(target == null || source == null) throw new NoSuchElementException("no vertex with such name");
        source.getEdges().removeIf(removed -> removed.getTargetVertex() == target);
    }

    public void readGraphFromFile(String filename) throws IOException, WrongGraphFormatException {
        GraphReader.readGraph(filename, this);
    }

    public void readGraphFromFile(String nodes, String edges) throws IOException, WrongGraphFormatException {
        GraphReader.readGraph(nodes, edges, this);
    }

    public void writeGraph() {
        GraphWriter.writeGraph(this);
    }

    /**
     * нужно вызывать после любой работы с графом по вычислению путей.
     * Выставлет поля в Vertex по умолчанию
     */
    public void validate(){
        for(Vertex v: vertices.values())
            v.validate();
    }

    private String parseDateToString(Date date){
        return formatter.format(date);
    }

    private Date parseMillisecondsToDate(long mills) throws ParseException {
        return formatter.parse(formatter.format(mills));
    }

    private Vertex getBestVertexToStartFrom(Vertex toVertex, List<Vertex> fromVertices) throws WrongOrderFormatException {
        String fromStr = "";
        double minPath = Double.MAX_VALUE;

        for(Vertex tmp: fromVertices) {
            Dijkstra.computePath(tmp);
            if(toVertex.getMinDistance() <= minPath) {
                fromStr = tmp.getName();
                minPath = toVertex.getMinDistance();
            }
            validate();
        }

        return vertices.getOrDefault(fromStr, null);
    }

    public List<Vertex> writeBestPath(Order order, Vertex fromVertex) throws WrongOrderFormatException, ParseException {
        Vertex toVertex = order.getVertex();

        if(fromVertex == null) throw new WrongOrderFormatException("no such points");
        if(toVertex == null) throw new WrongOrderFormatException("destination is more than 200 meters away from delivery zone");
        Dijkstra.computePath(fromVertex);

        System.out.println(order.getId() + ": ");

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
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

    public List<Vertex> writeBestPath(Order order, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        if(fromVertices.size() == 0) throw new WrongOrderFormatException("list is empty");
        Vertex toVertex = order.getVertex();
        if (toVertex == null) throw new WrongOrderFormatException("destination is more than 200 meters away from delivery zone");
        Vertex fromVertex = getBestVertexToStartFrom(toVertex, fromVertices);
        if(fromVertex == null) {
            System.out.println("No such path");
            return null;
        }
        return writeBestPath(order, fromVertex);
    }

    public List<Vertex> write2PathsAndTime(Order order, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        if(fromVertices.size() == 0) throw new WrongOrderFormatException("list is empty");
        List<Vertex> firstPath = writeBestPath(order, fromVertices);
        if(firstPath == null ||firstPath.size() <=10) return firstPath;

        List<Edge> temp;
        List<Vertex> secondPath;
        for(int i = 1; i < firstPath.size(); ++i){
            System.out.println();

            temp = firstPath.get(i).getEdges();
            firstPath.get(i).setEdges(new ArrayList<>());
            secondPath = writeBestPath(order, fromVertices);
            firstPath.get(i).setEdges(temp);

            if(secondPath != null) return secondPath;
        }
        return firstPath;
    }

    public void visualize(){
        tree.visualize("target/tree.png");
    }
}