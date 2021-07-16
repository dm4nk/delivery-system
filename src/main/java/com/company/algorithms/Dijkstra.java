package com.company.algorithms;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import java.util.*;

public class Dijkstra {

    private Dijkstra(){}

    /**
     * обязательно вызывается перед getShortestPathTo()
     * @param sourceVertex - откуда хотим идти
     */
    public static void computePath(Vertex sourceVertex) throws WrongOrderFormatException {
        if(sourceVertex == null) throw new WrongOrderFormatException("no such point");

        sourceVertex.computeMinPaths();
    }

    /**
     * перед применением необходимо вызвать функцию compute path и указать в качестве аргумента вершину,
     * @param targetVertex - куда хотим придти
     * @return кратчейший путь в обратном порядке
     */
    public static List<Vertex> getShortestPathTo(Vertex targetVertex) throws WrongOrderFormatException {

        if(targetVertex == null) throw new WrongOrderFormatException("no such point");

        List<Vertex> path = new ArrayList<>();

        path.add(targetVertex);

        Iterator<Vertex> it= targetVertex.prevIterator(targetVertex);
        while(it.hasNext()){
            path.add(it.next());
        }

        return path;
    }

    /**
     * Возвращает ближайшую точку в графе к указанным координатам
     * @return null, если точка находится дальше 200 метров от карты
     */
    public static Vertex calculateNearestVertexFromLatLon(Graph graph, double lon, double lat){
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

    /**
     * @param vertices - результат работы метода getShortestPathTo(),чтобы красиво вывести путь в консоль
     */
    public static void printVertexListAsPath(List<Vertex> vertices){
        if(vertices == null) {
            System.out.println("no such path: no routes to destination point");
            return;
        }
        ListIterator it = vertices.listIterator(vertices.size()-1);

        System.out.print(vertices.get(vertices.size()-1));
        while (it.hasPrevious())
            System.out.print(" -> "+ it.previous().toString());

        System.out.println();
    }
}
