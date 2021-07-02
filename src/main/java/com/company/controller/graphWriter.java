package com.company.controller;

import com.company.model.graph.Edge;
import com.company.model.graph.Vertex;
import com.company.model.graph.graph;

public class graphWriter {
    public static void writeGraph(graph graph){
        for(Vertex vertex : graph.getVertices().values())
            for (Edge edge: vertex.getEdges()){
                System.out.println(
                        edge.getStartVertex().getName() + " --" + edge.getWeight() + "-> " + edge.getTargetVertex().getName()
                );
            }
    }
}
