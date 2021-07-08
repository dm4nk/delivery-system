package com.company.controller;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Dijkstra {

    private Dijkstra(){}

    /**
     * обязательно вызывается перед getShortestPathTo()
     * @param sourceVertex - откуда хотим идти
     */
    public static void computePath(Vertex sourceVertex) throws WrongTaskFormatException {
        if(sourceVertex == null) throw new WrongTaskFormatException("no such point");

        sourceVertex.computeMinPaths();
    }

    /**
     * перед применением необходимо вызвать функцию compute path и указать в качестве аргумента вершину,
     * из которой хотим начать движание
     * возвращает лист вершин - оптимальный путь - в обратном порядке
     * @param targetVertex - куда хотим придти
     */
    public static List<Vertex> getShortestPathTo(Vertex targetVertex) throws WrongTaskFormatException {

        if(targetVertex == null) throw new WrongTaskFormatException("no such point");

        List<Vertex> path = new ArrayList<>();

        path.add(targetVertex);
        Iterator<Vertex> it= targetVertex.prevIterator(targetVertex);
        while(it.hasNext()){
            path.add(it.next());
        }

        return path;
    }

    public static Vertex calculateNearestVertex(Graph graph, double lon, double lat){
        //todo: 4
        Entry<String, Point> nearest;
        try {
            nearest = graph.getTree()
                    .nearest(Geometries.pointGeographic(lon, lat), 0.002, 1)
                    .toBlocking()
                    .single();
        } catch (NoSuchElementException e) {
            return null;
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
