package com.company.graphs.matrix;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.graphs.dynamic.Vertex;
import com.company.schedules.Task;
import com.company.graphReader;
import com.company.graphWriter;
import com.company.someDijkstra.dijkstraForMatrix.basicDijkstraForMatrixGraph;
import com.company.graphs.graph;

import java.io.IOException;
import java.util.Map;

public class matrixGraph implements com.company.graphs.graph {
    static double[][] graph = null;

    private static matrixGraph singleInstance = null;


    public static graph getInstance(){
        if(singleInstance == null){
            singleInstance = new matrixGraph();
        }

        return singleInstance;
    }

    private matrixGraph(){ }

    @Override
    public void addVertex(String name) {
        throw new UnsupportedOperationException("can not add vertex to matrix graph: method is on development :)");
    }

    @Override
    public Map<String, Vertex> getVertices() {
        throw new UnsupportedOperationException("can not add vertex to matrix graph: method is on development :)");
    }

    @Override
    public void addVertex(String name, double lat, double lon) {
        throw new UnsupportedOperationException("can not add vertex to matrix graph: method is on development :)");
    }

    @Override
    public void addEdge(double weight, String sourceVertex, String targetVertex) {
        throw new UnsupportedOperationException("can not add edge to matrix graph: method is on development :)");
    }

    public double[][] getMatrix(){
        return graph;
    }

    @Override
    public void readGraphFromFile(String filename) throws IOException, wrongGraphFormatException {
        graph = graphReader.readGraph(filename);
    }

    @Override
    public void readGraphFromFile(String nodes, String edges) throws IOException, wrongGraphFormatException {
        throw new UnsupportedOperationException("can not add edge to matrix graph: method is on development :)");
    }

    @Override
    public void writeGraph(){
        graphWriter.writeGraph(graph);
    }

    @Override
    public void writeBestPath(Task task) throws wrongTaskFormatException {
        basicDijkstraForMatrixGraph.printArrayListAsPath(basicDijkstraForMatrixGraph.showPath(
                graph,
                task
        ));
    }

//    public void write2BestPaths(Task task) throws wrongTaskFormatException {
//        ArrayList<Integer> temp = basicDijkstraForMatrixGraph.showPath(graph, task);
//        basicDijkstraForMatrixGraph.printArrayListAsPath(temp);
//
//        customDijkstraForMatrixGraph.printArrayListAsPath(
//                customDijkstraForMatrixGraph.showPath(graph,task, temp)
//        );
//    }
}