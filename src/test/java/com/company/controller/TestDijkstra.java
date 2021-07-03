package com.company.controller;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.model.graph.Vertex;
import com.company.model.graph.notSingletonGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDijkstra {
    @Test
    public void testComputePath() throws wrongGraphFormatException, wrongTaskFormatException {
        notSingletonGraph graph = new notSingletonGraph();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge(11, "1", "2");
        graph.addEdge(29, "1", "4");
        graph.addEdge(13, "1", "4");
        graph.addEdge(4, "2", "3");
        graph.addEdge(5, "3", "4");


        dijkstra.computePath(graph.getVertices().get("1"));


        Assert.assertEquals(0, graph.getVertices().get("1").getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(11, graph.getVertices().get("2").getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(15, graph.getVertices().get("3").getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(13, graph.getVertices().get("4").getMinDistance(), Double.MIN_VALUE);
    }

    @Test
    public void testGetShortestPathTo() throws wrongGraphFormatException, wrongTaskFormatException {
        notSingletonGraph graph = new notSingletonGraph();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge(99, "1", "4");
        graph.addEdge(1, "1", "2");
        graph.addEdge(9, "1", "3");
        graph.addEdge(5, "2", "3");
        graph.addEdge(16, "3", "4");

        dijkstra.computePath(graph.getVertices().get("1"));
        List<Vertex> actual = dijkstra.getShortestPathTo(graph.getVertices().get("4"));

        List<Vertex> expected = new ArrayList<>();
        expected.add(graph.getVertices().get("4"));
        expected.add(graph.getVertices().get("3"));
        expected.add(graph.getVertices().get("2"));
        expected.add(graph.getVertices().get("1"));

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        Assert.assertThrows(wrongTaskFormatException.class,()-> dijkstra.computePath(null));
        Assert.assertThrows(wrongTaskFormatException.class,()-> dijkstra.getShortestPathTo(null));
    }
}
