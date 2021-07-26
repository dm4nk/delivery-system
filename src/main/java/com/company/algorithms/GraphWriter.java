package com.company.algorithms;

import com.company.model.graph.Edge;
import com.company.model.graph.Vertex;
import com.company.model.graph.Graph;

public class GraphWriter {
    private GraphWriter(){}

    public static void writeGraph(Graph graph){
        for(Vertex vertex : graph.getVertices().values())
            for (Edge edge: vertex.getEdges())
                System.out.println(
                        edge.getStartVertex().getName() + " --" + edge.getWeight() + "-> " + edge.getTargetVertex().getName()
                );
    }
}
