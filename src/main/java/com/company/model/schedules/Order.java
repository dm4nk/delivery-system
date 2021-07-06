package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Edge;
import com.company.model.graph.Vertex;
import com.company.model.graph.Graph;
import com.company.controller.Dijkstra;

import java.text.SimpleDateFormat;
import java.util.*;

public class Order {
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
        return Dijkstra.calculateNearestVertex(graph, lon, lat);
    }

    public List<Vertex> writePathAndTime(Graph graph, String fromStreetIdentifier) throws WrongTaskFormatException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        List<Vertex> path;

        Vertex fromVertex = graph.getVertices().get(fromStreetIdentifier);
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

    public List<Vertex> writePathAndTime(Graph graph, String[] fromStreetIdentifiers) throws WrongTaskFormatException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        Vertex toVertex = calculateNearestVertex(graph);
        List<Vertex> path;

        if (toVertex == null) throw new WrongTaskFormatException("destination is more than 200 meters away from delivery zone");

        Vertex tmpVertex;

        String fromStr = "";
        Double minPath = Double.MAX_VALUE;

        for(String s: fromStreetIdentifiers) {
            tmpVertex = graph.getVertices().get(s);
            Dijkstra.computePath(tmpVertex);
            if(toVertex.getMinDistance() <= minPath) {
                fromStr = tmpVertex.getName();
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

    public <T> List<Vertex> write2PathsAndTime(Graph graph, T fromStreetIdentifier) throws WrongTaskFormatException {
        List<Vertex> first;
        if(fromStreetIdentifier instanceof String || fromStreetIdentifier instanceof String[]);
            else throw new IllegalArgumentException("second arg must be either String, or String[]");
        try {
            first = writePathAndTime(graph, (String) fromStreetIdentifier);
        }
        catch (Exception e){
            first = writePathAndTime(graph, (String[]) fromStreetIdentifier);
            if(((String[]) fromStreetIdentifier).length == 0) throw new IllegalArgumentException("Empty string");
        }

        if(first.size() <=10) return first;

        List<Edge> temp;
        List<Vertex> second;
        for(int i = 1; i < first.size(); ++i){
            temp = first.get(i).getEdges();
            first.get(i).setEdges(new ArrayList<>());
            System.out.println();
            try {
                second = writePathAndTime(graph, (String) fromStreetIdentifier);
            }
            catch (Exception e){
                second = writePathAndTime(graph, (String[]) fromStreetIdentifier);
            }

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