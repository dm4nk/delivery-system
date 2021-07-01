package com.company.schedules;

import com.company.Exceptions.wrongTaskFormatException;
import com.company.graph.Vertex;
import com.company.graph.graph;
import com.company.someDijkstra.dijkstraForDynamicGraph.basicDijkstraForDynamicGraph;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class order {
    Date date;
    double lat;
    double lon;

    public order(Date date, double lat, double lon){
        this.date = date;
        this.lat = lat;
        this.lon = lon;
    }

    public Vertex calculateNearestVertex(graph graph){

        Map<String, Vertex> vertexMap = graph.getVertices();
        Vertex nearest = new Vertex("" , Double.MAX_VALUE, Double.MAX_VALUE);

        //TODO: сделать не за линейное время
        for(Vertex temp: vertexMap.values()){
            if(
                    Math.pow(temp.getLat()-lat,  2) + Math.pow(temp.getLon()-lon, 2) <
                            Math.pow(nearest.getLat()-lat,  2) + Math.pow(nearest.getLon()-lon, 2)
            )
                nearest = temp;
        }
        return nearest;
    }

    public void writePathAndTime(graph graph, String fromStreetIdentifier) throws wrongTaskFormatException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);

        Vertex fromVertex = calculateNearestVertex(graph);
        Vertex toVertex = graph.getVertices().get(fromStreetIdentifier);

        if(fromVertex == null || toVertex == null) throw new wrongTaskFormatException("no such points");

        basicDijkstraForDynamicGraph.computePath(fromVertex);

        if(toVertex.getMinDistance() == Double.MAX_VALUE){
            System.out.println("No such path");
            return;
        }
        System.out.println("Order time: " + formatter.format(date));
        System.out.print("path: ");
        basicDijkstraForDynamicGraph.printVertexListAsPath(
                basicDijkstraForDynamicGraph.getShortestPathTo(toVertex)
        );

        System.out.println("Time required: " + toVertex.getMinDistance());

        System.out.println("Approximate delivery time:" + formatter.format(date.getTime() + toVertex.getMinDistance()*60000));
    }
}