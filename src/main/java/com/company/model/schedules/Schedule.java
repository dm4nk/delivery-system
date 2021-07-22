package com.company.model.schedules;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.graph.Vertex;

import java.text.ParseException;
import java.util.List;

public interface Schedule {
    void addOrder(Graph graph, Order order) throws WrongOrderFormatException;
    void writePaths(Graph graph, List<Vertex> fromVertices) throws WrongOrderFormatException, ParseException;
}
