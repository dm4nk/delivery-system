package com.company.model.graph;

import com.company.Exceptions.WrongGraphFormatException;
import org.junit.Assert;
import org.junit.Test;

public class TestGraph {
    @Test
    public void testAddVertex() throws WrongGraphFormatException {
        NotSingletonGraph actual = new NotSingletonGraph();
        actual.addVertex("1");
        actual.addVertex("2");

        Assert.assertEquals(2, actual.getVertices().size());
        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addVertex("2"));
    }
    @Test
    public void testAddEdge() throws WrongGraphFormatException {
        NotSingletonGraph actual = new NotSingletonGraph();
        actual.addVertex("1");
        actual.addVertex("2");

        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addEdge(-1, "1", "2"));
        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addEdge(-1, "notExistingVertex", "2"));
        Assert.assertThrows(WrongGraphFormatException.class, ()->actual.addEdge(-1, "1", "notExistingVertex"));
    }
}
