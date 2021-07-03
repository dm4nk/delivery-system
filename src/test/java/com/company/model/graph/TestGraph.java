package com.company.model.graph;

import com.company.Exceptions.wrongGraphFormatException;
import org.junit.Assert;
import org.junit.Test;

public class TestGraph {
    @Test
    public void testAddVertex() throws wrongGraphFormatException {
        notSingletonGraph actual = new notSingletonGraph();
        actual.addVertex("1");
        actual.addVertex("2");

        Assert.assertEquals(2, actual.getVertices().size());
        Assert.assertThrows(wrongGraphFormatException.class, ()->actual.addVertex("2"));
    }
    @Test
    public void testAddEdge() throws wrongGraphFormatException {
        notSingletonGraph actual = new notSingletonGraph();
        actual.addVertex("1");
        actual.addVertex("2");

        Assert.assertThrows(wrongGraphFormatException.class, ()->actual.addEdge(-1, "1", "2"));
        Assert.assertThrows(wrongGraphFormatException.class, ()->actual.addEdge(-1, "notExistingVertex", "2"));
        Assert.assertThrows(wrongGraphFormatException.class, ()->actual.addEdge(-1, "1", "notExistingVertex"));
    }
}
