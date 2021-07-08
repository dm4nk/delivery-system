package com.company.Mains;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.model.graph.Graph;

import java.io.IOException;

public class MainTemp {
    public static void main(String[] args) throws WrongGraphFormatException, IOException {

        String path = "src\\main\\resources\\dataset\\";
        Graph.getInstance().readGraphFromFile(path + "nodes.csv", path + "edges.csv");

        Graph.getInstance().writeGraph();
    }
}