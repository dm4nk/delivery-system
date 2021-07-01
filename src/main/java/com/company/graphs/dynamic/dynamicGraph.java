package com.company.graphs.dynamic;
import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.schedules.Task;
import com.company.graphReader;
import com.company.someDijkstra.dijkstraForDynamicGraph.basicDijkstraForDynamicGraph;
import com.company.graphs.graph;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class dynamicGraph implements com.company.graphs.graph {
    private static Map<String, Vertex> vertices = new HashMap<>();

    private static dynamicGraph singleInstance = null;

    public static graph getInstance(){
        if(singleInstance == null){
            singleInstance = new dynamicGraph();
        }

        return singleInstance;
    }

    private dynamicGraph(){}

    public void addVertex(String name){
        vertices.put(name, new Vertex(name));
    }

    @Override
    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public void addVertex(String name, double lat, double lon){
        vertices.put(name, new Vertex(name, lat, lon));
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

    @Override
    public void readGraphFromFile(String filename) throws IOException, wrongGraphFormatException {

        double[][] graph = graphReader.readGraph(filename);

        for(int i = 0; i < graph.length; ++i)
            this.addVertex(Double.toString(i));

        for(int i = 0; i < graph.length; ++i)
            for (int j = 0; j < graph.length; ++j)
                if(graph[i][j] != Double.MAX_VALUE )
                    this.addEdge(graph[i][j], Double.toString(i), Double.toString(j));
    }

    @Override
    public void readGraphFromFile(String nodes, String edges) throws IOException, wrongGraphFormatException {
        if(!nodes.endsWith(".csv") || !edges.endsWith(".csv"))
            throw new wrongGraphFormatException("can only read from .csv files");

        try (CSVReader csvReader = new CSVReader(new FileReader(nodes))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                this.addVertex(
                        values[0],
                        Double.parseDouble(values[1]),
                        Double.parseDouble(values[2])
                );
            }
        }

        try (CSVReader csvReader = new CSVReader(new FileReader(edges))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                this.addEdge(Double.parseDouble(values[3])/Double.parseDouble(values[5])*3.6, values[1], values[2]);
            }
        }
    }

    @Override
    public void writeGraph() {
        for(Vertex vertex : vertices.values())
            for (Edge edge: vertex.getEdges()){
                System.out.println(
                        edge.getStartVertex().getName() + " --" + edge.getWeight() + "-> " + edge.getTargetVertex().getName()
                );
            }

    }

    @Override
    public void writeBestPath(Task task) throws wrongTaskFormatException {
        Vertex fromVertex = vertices.get(task.getFrom());
        Vertex toVertex = vertices.get(task.getTo());

        if(fromVertex == null || toVertex == null) throw new wrongTaskFormatException("no such points");

        basicDijkstraForDynamicGraph.computePath(fromVertex);

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return;
        }
        System.out.print("path: ");
        basicDijkstraForDynamicGraph.printVertexListAsPath(
                basicDijkstraForDynamicGraph.getShortestPathTo(toVertex)
        );
        if(toVertex.getMinDistance() < task.timeRequired())
            System.out.println("time required: " + toVertex.getMinDistance());
        else
            System.out.println("NOT enough time! time required: " +
                    task.timeRequired() + toVertex.getMinDistance()
            );
    }
}
