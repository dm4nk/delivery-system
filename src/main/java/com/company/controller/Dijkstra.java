package com.company.controller;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Edge;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    private Dijkstra(){}

    //обязательно вызывается перед getShortestPathTo()
    public static void computePath(Vertex sourceVertex) throws WrongTaskFormatException {

        if(sourceVertex == null) throw new WrongTaskFormatException("no such point");

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
    public static List<Vertex> getShortestPathTo(Vertex targetVertex) throws WrongTaskFormatException {

        if(targetVertex == null) throw new WrongTaskFormatException("no such point");

        List<Vertex> path = new ArrayList<>();

        for (Vertex vertex = targetVertex; vertex != null; vertex = vertex.getPreviousVertex()) {
            //System.out.println(vertex.getName());
            path.add(vertex);
        }

        return path;
    }

    public static Vertex calculateNearestVertex(Graph graph, double lon, double lat){
        List<Entry<String, Point>> list = graph.getTree()
                .search(Geometries.pointGeographic(lon, lat), 0.002).toList()
                .toBlocking()
                .single();

        if(list.size() == 0) return null;

        Entry<String, Point> nearest = list.get(0);
        for(Entry<String, Point> temp: list){
            if(
                    Math.pow(temp.geometry().y()-lat,  2) + Math.pow(temp.geometry().x()-lon, 2) <
                            Math.pow(nearest.geometry().y()-lat,  2) + Math.pow(nearest.geometry().x()-lon, 2)
            )
                nearest = temp;
        }

        return graph.getVertices().get(nearest.value());
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
