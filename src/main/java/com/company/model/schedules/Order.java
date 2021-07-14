package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Edge;
import com.company.model.graph.Vertex;
import com.company.model.graph.Graph;
import com.company.algorithms.Dijkstra;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order implements Serializable {
    Date date;
    double lat;
    double lon;

    public Order(Date date, double lon, double lat){
        this.date = date;
        this.lat = lat;
        this.lon = lon;
    }

    public Date getDate() {
        return date;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Vertex calculateNearestVertex(Graph graph){
        return Dijkstra.calculateNearestVertexFromLatLon(graph, lon, lat);
    }

    public List<Vertex> writePathAndTime(Graph graph, Vertex fromVertex) throws WrongTaskFormatException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        List<Vertex> path;

        //Vertex fromVertex = graph.getVertices().get(fromStreetIdentifier);
        Vertex toVertex = calculateNearestVertex(graph);

        if(fromVertex == null) throw new WrongTaskFormatException("no such points");
        if(toVertex == null) throw new WrongTaskFormatException("destination is more than 200 meters away from delivery zone");
        Dijkstra.computePath(fromVertex);

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return new ArrayList<>();
        }
        System.out.println("Order time: " + formatter.format(date));
        System.out.print("path: ");
        path = Dijkstra.getShortestPathTo(toVertex);
        Dijkstra.printVertexListAsPath(
                path
        );

        System.out.println("Time required: " + toVertex.getMinDistance());

        System.out.println("Approximate delivery time:" + formatter.format(date.getTime() + toVertex.getMinDistance()*60000));

        for(Vertex v: graph.getVertices().values()){
            v.setMinDistance(Double.MAX_VALUE);
            v.setPreviousVertex(null);
        }
        return path;
    }

    public List<Vertex> writePathAndTime(Graph graph, List<Vertex> fromVertexes) throws WrongTaskFormatException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        Vertex toVertex = calculateNearestVertex(graph);
        List<Vertex> path;

        if (toVertex == null) throw new WrongTaskFormatException("destination is more than 200 meters away from delivery zone");

        Vertex tmpVertex;

        String fromStr = "";
        Double minPath = Double.MAX_VALUE;

        for(Vertex tmp: fromVertexes) {
            //tmpVertex = graph.getVertices().get(s);
            Dijkstra.computePath(tmp);
            if(toVertex.getMinDistance() <= minPath) {
                fromStr = tmp.getName();
                minPath = toVertex.getMinDistance();
            }

            for(Vertex v: graph.getVertices().values()){
                v.setMinDistance(Double.MAX_VALUE);
                v.setPreviousVertex(null);
                v.setVisited(false);
            }
        }
        Vertex fromVertex = graph.getVertices().get(fromStr);
        Dijkstra.computePath(fromVertex);

        if(fromVertex.getMinDistance() == Double.MAX_VALUE) throw new WrongTaskFormatException("no such points");

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return new ArrayList<>();
        }
        System.out.println("Order time: " + formatter.format(date));
        System.out.print("path: ");

        path = Dijkstra.getShortestPathTo(toVertex);
        Dijkstra.printVertexListAsPath(
                path
        );

        System.out.println("Time required: " + toVertex.getMinDistance());

        System.out.println("Approximate delivery time:" + formatter.format(date.getTime() + toVertex.getMinDistance()*60000));

        for(Vertex v: graph.getVertices().values()){
            v.setMinDistance(Double.MAX_VALUE);
            v.setPreviousVertex(null);
            v.setVisited(false);
        }
        return path;
    }

    public List<Vertex> write2PathsAndTime(Graph graph, List<Vertex> vertices) throws WrongTaskFormatException {
        if(vertices.size() == 0) throw new WrongTaskFormatException("list is empty");
        List<Vertex> first = writePathAndTime(graph, vertices);
        if(first.size() <=10 || vertices.size() == 1) return first;

        List<Edge> temp;
        List<Vertex> second;
        for(int i = 1; i < first.size(); ++i){
            temp = first.get(i).getEdges();
            first.get(i).setEdges(new ArrayList<>());
            System.out.println();
            second = writePathAndTime(graph, vertices);
            first.get(i).setEdges(temp);
            if(second.size() != 0){
                return second;
            }
        }
        return first;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.lat, lat) == 0 &&
                Double.compare(order.lon, lon) == 0 &&
                date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, lat, lon);
    }
}