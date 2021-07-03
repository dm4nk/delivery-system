package com.company.controller;

import com.company.Exceptions.wrongTaskFormatException;
import com.company.model.graph.Edge;
import com.company.model.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class dijkstra {

    //обязательно вызывается перед getShortestPathTo()
    public static void computePath(Vertex sourceVertex) throws wrongTaskFormatException {

        if(sourceVertex == null) throw new wrongTaskFormatException("no such point");

        sourceVertex.setMinDistance(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceVertex);

        while (!priorityQueue.isEmpty()) {
            Vertex vertex = priorityQueue.poll();

            for (Edge edge : vertex.getEdges()) {
                Vertex v = edge.getTargetVertex();
                double weight = edge.getWeight();
                double minDistance = vertex.getMinDistance() + weight;

                if (minDistance < v.getMinDistance()) {
                    priorityQueue.remove(vertex);
                    v.setPreviousVertex(vertex);
                    v.setMinDistance(minDistance);
                    priorityQueue.add(v);
                }
            }
        }
    }

    //перед применением необходимо вызвать функцию compute path и указать в качестве аргумента вершину,
    //из которой хотим начать движание
    //возвращает лист вершин - оптимальный путь - в обратном порядке
    public static List<Vertex> getShortestPathTo(Vertex targetVertex) throws wrongTaskFormatException {

        if(targetVertex == null) throw new wrongTaskFormatException("no such point");

        List<Vertex> path = new ArrayList<>();

        for (Vertex vertex = targetVertex; vertex != null; vertex = vertex.getPreviousVertex()) {
            path.add(vertex);
        }

        return path;
    }

    public static void printVertexListAsPath(List<Vertex> vertices){
        if(vertices == null) {
            System.out.println("no such path: no routes to destination point");
            return;
        }

        for (int i = vertices.size(); --i > 0;) {
            System.out.print(vertices.get(i).getName() + " -> ");
        }
        System.out.println(vertices.get(0).getName());
    }
}
