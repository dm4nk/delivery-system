package model.graph;

import exceptions.WrongGraphFormatException;
import org.junit.Assert;
import org.junit.Test;

public class TestNotSingletonGraph {
    @Test
    public void testNotSingletonGraph() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();

        final long testVertexThatShouldNotExistInRealGraph = Long.MIN_VALUE;
        actual.addVertex(testVertexThatShouldNotExistInRealGraph);

        Assert.assertNull(Graph.getInstance().getVertex(testVertexThatShouldNotExistInRealGraph));

        Assert.assertNotEquals(actual, Graph.getInstance());
    }
}
