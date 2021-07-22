package com.company.model.graph;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongOrderFormatException;
import com.company.algorithms.GraphWriter;
import com.company.model.schedules.Order;
import com.company.algorithms.GraphReader;
import com.company.algorithms.Dijkstra;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Vertex getVertex(String id){
        return vertices.getOrDefault(id, null);
    }

    public void removeVertex(String name){
        if(!vertices.containsKey(name)) throw new NoSuchElementException("no vertex with such name");
        Vertex removed = vertices.get(name);

        vertices.values()
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

    /**
     * нужно вызывать после любой работы с графом по вычислению путей.
     * Выставлет поля в Vertex по умолчанию
     */
    public void validate(){
        for(Vertex v: vertices.values())
            v.validate();
    }

    public List<Vertex> writeBestPath(Order order, Vertex fromVertex) throws WrongOrderFormatException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        List<Vertex> path;
        //Vertex toVertex = order.calculateNearestVertex(this);
        Vertex toVertex = order.getVertex();

        if(fromVertex == null) throw new WrongOrderFormatException("no such points");
        if(toVertex == null) throw new WrongOrderFormatException("destination is more than 200 meters away from delivery zone");
        Dijkstra.computePath(fromVertex);

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return new ArrayList<>();
        }
        System.out.println("Dispatch time: " + formatter.format(order.getDispatchTime()));
        System.out.print("path: ");
        path = Dijkstra.getShortestPathTo(toVertex);
        Dijkstra.printVertexListAsPath(
                path
        );

        System.out.println("Time required: " + toVertex.getMinDistance());

        order.setArrivalTime(formatter.parse(formatter.format(order.getDispatchTime().getTime() + toVertex.getMinDistance()*60000)));
        System.out.println("Approximate delivery time: " + formatter.format(order.getArrivalTime()));

        validate();
        return path;
    }

    public List<Vertex> writeBestPath(Order order, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        //Vertex toVertex = order.calculateNearestVertex(this);
        Vertex toVertex = order.getVertex();
        List<Vertex> path;

        if (toVertex == null) throw new WrongOrderFormatException("destination is more than 200 meters away from delivery zone");

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

        Vertex fromVertex = getVertices().get(fromStr);
        Dijkstra.computePath(fromVertex);

        if(fromVertex.getMinDistance() == Double.MAX_VALUE) throw new WrongOrderFormatException("no such points");

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return new ArrayList<>();
        }
        System.out.println("Dispatch time: " + formatter.format(order.getDispatchTime()));
        System.out.print("path: ");

        path = Dijkstra.getShortestPathTo(toVertex);
        Dijkstra.printVertexListAsPath(
                path
        );

        System.out.println("Time required: " + toVertex.getMinDistance());

        order.setArrivalTime(formatter.parse(formatter.format(order.getDispatchTime().getTime() + toVertex.getMinDistance()*60000)));
        System.out.println("Approximate delivery time: " + formatter.format(order.getArrivalTime()));

        validate();
        return path;
    }

    public List<Vertex> write2PathsAndTime(Order order, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException {
        if(fromVertices.size() == 0) throw new WrongOrderFormatException("list is empty");
        List<Vertex> first = writeBestPath(order, fromVertices);
        if(first.size() <=10 || vertices.size() == 1) return first;

        List<Edge> temp;
        List<Vertex> second;
        for(int i = 1; i < first.size(); ++i){
            temp = first.get(i).getEdges();
            first.get(i).setEdges(new ArrayList<>());
            System.out.println();
            second = writeBestPath(order, fromVertices);
            first.get(i).setEdges(temp);
            if(second.size() != 0){
                return second;
            }
        }
        return first;
    }

    public void visualize(){
        tree.visualize(1920, 1080).save("target/mytree.png");
    }
}