package com.company.model.graph;

import com.company.Exceptions.wrongGraphFormatException;
import org.junit.Assert;
import org.junit.Test;

public class TestNotSingletonGraph {
    @Test
    public void testNotSingletonGraph() throws wrongGraphFormatException {
        notSingletonGraph actual = new notSingletonGraph();

        actual.addVertex("testVertexThatShouldNotExistInRealGraph");

        Assert.assertNull(graph.getInstance().getVertices().getOrDefault("testVertexThatShouldNotExistInRealGraph", null));

        Assert.assertNotEquals(actual, graph.getInstance());
    }
}
