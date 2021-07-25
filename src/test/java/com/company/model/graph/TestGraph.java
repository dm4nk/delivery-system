package com.company.model.graph;

import com.company.Exceptions.WrongGraphFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public class TestGraph {
    @Test
    public void testAddVertex() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex("1");
        actual.addVertex("2");

        Assert.assertEquals(2, actual.getVertices().size());
        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addVertex("2"));
    }
    @Test
    public void testRemoveVertex() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex("1", 1, 1);
        actual.addVertex("2", 1, 1);
        actual.addVertex("3", 1, 1);

        actual.removeVertex("1");

        Assert.assertEquals(2, actual.getVertices().size());
        Assert.assertEquals(2, actual.getTree().size());

        Assert.assertNull(actual.getVertices().getOrDefault("1", null));
        Assert.assertNotNull(actual.getVertices().getOrDefault("2", null));
        Assert.assertNotNull(actual.getVertices().getOrDefault("3", null));

    }
    @Test
    public void testAddEdge() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex("1");
        actual.addVertex("2");

        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addEdge(-1, "1", "2"));
        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addEdge(-1, "notExistingVertex", "2"));
        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addEdge(-1, "1", "notExistingVertex"));
    }
    @Test
    public void testRemoveEdge() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex("1");
        actual.addVertex("2");
        actual.addVertex("3");

        actual.addEdge(12, "1", "2");
        actual.addEdge(23, "2", "3");

        actual.removeEdge("1", "2");

        Assert.assertEquals(0, actual.getVertices().get("1").getEdges().size());
        Assert.assertThrows(NoSuchElementException.class, ()->actual.removeEdge("111", "222"));
    }
}
