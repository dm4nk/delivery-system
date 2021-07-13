package com.company.model.graph;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongTaskFormatException;
import com.company.controller.GraphWriter;
import com.company.model.schedules.Order;
import com.company.model.schedules.Task;
import com.company.controller.GraphReader;
import com.company.controller.Dijkstra;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import java.io.IOException;
import java.util.*;

public class Graph {
    private Map<String, Vertex> vertices = new HashMap<>();
    private RTree<String, Point> tree = RTree.star().maxChildren(4).create();

    private static Graph singleInstance = null;

    public static Graph getInstance(){
        if(singleInstance == null){
            singleInstance = new Graph();
        }
        return singleInstance;
    }

    protected Graph(){}

    public  int size(){
        return vertices.size();
    }

    public void addVertex(String name) throws WrongGraphFormatException {
        if(vertices.put(name, new Vertex(name)) != null) throw new WrongGraphFormatException("such point already exists");
        tree = tree.add(name, Geometries.pointGeographic(180, 180));
    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public RTree<String, Point> getTree(){
        return tree;
    }

    public void addVertex(String name, double lon, double lat) throws WrongGraphFormatException {
        if(vertices.put(name, new Vertex(name, lon, lat)) != null) throw new WrongGraphFormatException("such point already exists");
        tree = tree.add(name, Geometries.pointGeographic(lon, lat));
    }

    public void removeVertex(String name){
        if(!vertices.containsKey(name)) throw new NoSuchElementException("no vertex with such name");
        Vertex removed = vertices.get(name);
//        for (Vertex v: vertices.values())
//            v.getEdges().removeIf(e -> e.getTargetVertex() == removed);

        vertices.values()
                .stream()
                .forEach(v -> v.getEdges().removeIf(e -> e.getTargetVertex() == removed));

        tree = tree.delete(name, Geometries.pointGeographic(removed.getLon(), removed.getLat()));

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

        for(Edge removed : source.getEdges()){
            if(removed.getTargetVertex() == target) source.getEdges().remove(removed);
            return;
        }

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

    public void writeBestPath(Task task) throws WrongTaskFormatException {
        Vertex fromVertex = vertices.get(task.getFrom());
        Vertex toVertex = vertices.get(task.getTo());

        if(fromVertex == null || toVertex == null) throw new WrongTaskFormatException("no such points");

        Dijkstra.computePath(fromVertex);

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return;
        }
        System.out.print("path: ");
        Dijkstra.printVertexListAsPath(
                Dijkstra.getShortestPathTo(toVertex)
        );
        if(toVertex.getMinDistance() < task.timeRequired())
            System.out.println("time required: " + toVertex.getMinDistance());
        else
            System.out.println("NOT enough time! time required: " +
                    task.timeRequired() + toVertex.getMinDistance()
            );
    }

    public List<Vertex> writeBestPath(Order order, String restaurantStreetID) throws WrongTaskFormatException {
        return order.writePathAndTime(this, restaurantStreetID);
    }

    public List<Vertex> writeBestPath(Order order, String[] restaurantStreetIDs) throws WrongTaskFormatException {
        return order.writePathAndTime(this, restaurantStreetIDs);
    }

    public <T> List<Vertex> write2PathsAndTime(Order order, T fromStreetIdentifier) throws WrongTaskFormatException {
        return order.write2PathsAndTime(this, fromStreetIdentifier);
    }

    public void visualize(){
        tree.visualize(1920, 1080).save("target/mytree.png");
    }
}