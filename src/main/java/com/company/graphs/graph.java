package com.company.graphs;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.graphs.dynamic.Vertex;
import com.company.schedules.Task;

import java.io.IOException;
import java.util.Map;

public interface graph {

    void addVertex(String name);

    Map<String, Vertex> getVertices();

    void addVertex(String name, double lat, double lon);

    void addEdge(double weight, String sourceVertex, String targetVertex) throws wrongGraphFormatException;

    void readGraphFromFile(String filename) throws IOException, wrongGraphFormatException;

    void readGraphFromFile(String nodes, String edges) throws IOException, wrongGraphFormatException;

    void writeGraph();

    void writeBestPath(Task task) throws wrongTaskFormatException;
}
