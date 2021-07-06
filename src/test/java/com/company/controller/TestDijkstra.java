package com.company.controller;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Vertex;
import com.company.model.graph.NotSingletonGraph;
import com.company.model.schedules.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDijkstra {
    @Test
    public void testComputePath() throws WrongGraphFormatException, WrongTaskFormatException {
        NotSingletonGraph graph = new NotSingletonGraph();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge(11, "1", "2");
        graph.addEdge(29, "1", "4");
        graph.addEdge(13, "1", "4");
        graph.addEdge(4, "2", "3");
        graph.addEdge(5, "3", "4");


        Dijkstra.computePath(graph.getVertices().get("1"));


        Assert.assertEquals(0, graph.getVertices().get("1").getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(11, graph.getVertices().get("2").getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(15, graph.getVertices().get("3").getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(13, graph.getVertices().get("4").getMinDistance(), Double.MIN_VALUE);
    }

    @Test
    public void testGetShortestPathTo() throws WrongGraphFormatException, WrongTaskFormatException {
        NotSingletonGraph graph = new NotSingletonGraph();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge(99, "1", "4");
        graph.addEdge(1, "1", "2");
        graph.addEdge(9, "1", "3");
        graph.addEdge(5, "2", "3");
        graph.addEdge(16, "3", "4");

        Dijkstra.computePath(graph.getVertices().get("1"));
        List<Vertex> actual = Dijkstra.getShortestPathTo(graph.getVertices().get("4"));

        List<Vertex> expected = new ArrayList<>();
        expected.add(graph.getVertices().get("4"));
        expected.add(graph.getVertices().get("3"));
        expected.add(graph.getVertices().get("2"));
        expected.add(graph.getVertices().get("1"));

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        Assert.assertThrows(WrongTaskFormatException.class,()-> Dijkstra.computePath(null));
        Assert.assertThrows(WrongTaskFormatException.class,()-> Dijkstra.getShortestPathTo(null));
    }

    @Test
    public void testCalculateNearestVertex() throws WrongGraphFormatException {
        NotSingletonGraph graph = new NotSingletonGraph();

        graph.addVertex("1", 53.253709, 50.209983);
        graph.addVertex("2", 53.256407, 50.212732);

        Vertex actual = Dijkstra.calculateNearestVertex(graph, 53.252608, 50.210996);

        Assert.assertEquals("1", actual.getName());
        Assert.assertNull(Dijkstra.calculateNearestVertex(graph, 53.253554, 50.200895));
    }
}
