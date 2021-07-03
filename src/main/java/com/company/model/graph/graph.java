package com.company.model.graph;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.controller.graphWriter;
import com.company.model.schedules.task;
import com.company.controller.graphReader;
import com.company.controller.dijkstra;
import java.io.IOException;
import java.util.*;

public class graph  {
    private Map<String, Vertex> vertices = new HashMap<>();
    private static graph singleInstance = null;

    public static graph getInstance(){
        if(singleInstance == null){
            singleInstance = new graph();
        }

        return singleInstance;
    }

    protected graph(){}

    public  int size(){
        return vertices.size();
    }

    public void addVertex(String name) throws wrongGraphFormatException {

        if(vertices.put(name, new Vertex(name)) != null) throw new wrongGraphFormatException("such point already exists");

    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public void addVertex(String name, double lat, double lon) throws wrongGraphFormatException {
        if(vertices.put(name, new Vertex(name, lat, lon)) != null) throw new wrongGraphFormatException("such point already exists");
    }

    public void addEdge(double weight, String sourceVertex, String targetVertex) throws wrongGraphFormatException {
        if(sourceVertex.equals(targetVertex)) throw new wrongGraphFormatException("source vertex equals to target vertex");
        if(weight<0)throw  new wrongGraphFormatException("negative weight");
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
            throw new wrongGraphFormatException("no such points");
        }
    }

    public void readGraphFromFile(String filename) throws IOException, wrongGraphFormatException {
        graphReader.readGraph(filename, this);
    }

    public void readGraphFromFile(String nodes, String edges) throws IOException, wrongGraphFormatException {
        graphReader.readGraph(nodes, edges, this);
    }

    public void writeGraph() {
        graphWriter.writeGraph(this);
    }

    public void writeBestPath(task task) throws wrongTaskFormatException {
        Vertex fromVertex = vertices.get(task.getFrom());
        Vertex toVertex = vertices.get(task.getTo());

        if(fromVertex == null || toVertex == null) throw new wrongTaskFormatException("no such points");

        dijkstra.computePath(fromVertex);

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return;
        }
        System.out.print("path: ");
        dijkstra.printVertexListAsPath(
                dijkstra.getShortestPathTo(toVertex)
        );
        if(toVertex.getMinDistance() < task.timeRequired())
            System.out.println("time required: " + toVertex.getMinDistance());
        else
            System.out.println("NOT enough time! time required: " +
                    task.timeRequired() + toVertex.getMinDistance()
            );
    }
}
