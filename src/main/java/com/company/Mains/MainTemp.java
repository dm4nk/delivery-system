package com.company.Mains;

import com.company.model.graph.graph;
import com.company.model.graph.notSingletonGraph;

public class MainTemp {
    public static void main(String[] args) throws Exception {
        graph.getInstance();

        graph.getInstance().addVertex("a");
        graph.getInstance().addVertex("b");
        graph.getInstance().addEdge(10, "a", "b");

        notSingletonGraph gr = new notSingletonGraph();

        gr.addVertex("1");
        gr.addVertex("2");
        gr.addEdge(11, "1",  "2");

        gr.writeGraph();
    }
}