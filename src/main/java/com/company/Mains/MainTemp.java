package com.company.Mains;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.model.graph.graph;
import com.company.model.graph.notSingletonGraph;

public class MainTemp {
    public static void main(String[] args) throws wrongGraphFormatException {
        graph.getInstance().addVertex("1");
        graph.getInstance().addVertex("1");


    }
}