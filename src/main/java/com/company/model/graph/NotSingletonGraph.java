package com.company.model.graph;

public class NotSingletonGraph extends Graph {
    private NotSingletonGraph(){}

    public static NotSingletonGraph create(){
        return new NotSingletonGraph();
    }
}