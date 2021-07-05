package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Vertex;
import com.company.model.graph.Graph;
import com.company.controller.Dijkstra;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

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

    public void writePathAndTime(Graph graph, String fromStreetIdentifier) throws WrongTaskFormatException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);

        Vertex fromVertex = calculateNearestVertex(graph);
        Vertex toVertex = graph.getVertices().get(fromStreetIdentifier);

        if(fromVertex == null) throw new WrongTaskFormatException("destination is more than 200 meters away from delivery zone");
        if(toVertex == null) throw new WrongTaskFormatException("no such points");
        Dijkstra.computePath(fromVertex);

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return;
        }
        System.out.println("Order time: " + formatter.format(date));
        System.out.print("path: ");
        Dijkstra.printVertexListAsPath(
                Dijkstra.getShortestPathTo(toVertex)
        );

        System.out.println("Time required: " + toVertex.getMinDistance());

        System.out.println("Approximate delivery time:" + formatter.format(date.getTime() + toVertex.getMinDistance()*60000));
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
