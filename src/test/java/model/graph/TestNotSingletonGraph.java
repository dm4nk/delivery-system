package model.graph;

import exceptions.WrongGraphFormatException;
import model.graph.Graph;
import model.graph.NotSingletonGraph;
import org.junit.Assert;
import org.junit.Test;

public class TestNotSingletonGraph {
    @Test
    public void testNotSingletonGraph() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();

        actual.addVertex("testVertexThatShouldNotExistInRealGraph");

        Assert.assertNull(Graph.getInstance().getVertices().getOrDefault("testVertexThatShouldNotExistInRealGraph", null));

        Assert.assertNotEquals(actual, Graph.getInstance());
    }
}
