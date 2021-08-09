package model.graph;

import exceptions.WrongGraphFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public class TestGraph {

    @Test
    public void testAddVertex() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex(model.graph.Vertex.create(1));
        actual.addVertex(model.graph.Vertex.create(2));

        Assert.assertEquals(2, actual.getVertices().size());

        //should not add vertex with already existing id
        Assert.assertThrows(WrongGraphFormatException.class, () -> actual.addVertex(model.graph.Vertex.create(2)));
    }

    @Test
    public void testRemoveVertex() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex(model.graph.Vertex.create(1, 1, 1));
        actual.addVertex(model.graph.Vertex.create(2, 1, 1));
        actual.addVertex(model.graph.Vertex.create(3, 1, 1));

        actual.removeVertex(1);

        Assert.assertEquals(2, actual.size());
        Assert.assertEquals(2, actual.getTree().size());

        Assert.assertNull(actual.getVertex(1));
        Assert.assertNotNull(actual.getVertex(2));
        Assert.assertNotNull(actual.getVertex(3));
    }

    @Test
    public void testAddEdge() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex(model.graph.Vertex.create(1));
        actual.addVertex(model.graph.Vertex.create(2));

        //negative weight is not allowed
        Assert.assertThrows(WrongGraphFormatException.class, () -> actual.addEdge(Edge.create(-1, actual.getVertex(1), actual.getVertex(2))));

        //not existing source vertex
        Assert.assertThrows(NullPointerException.class, () -> actual.addEdge(Edge.create(1, actual.getVertex(Long.MAX_VALUE), actual.getVertex(2))));

        //not existing target vertex
        Assert.assertThrows(NullPointerException.class, () -> actual.addEdge(Edge.create(1, actual.getVertex(1), actual.getVertex(Long.MAX_VALUE))));
    }

    @Test
    public void testRemoveEdge() throws WrongGraphFormatException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        actual.addVertex(model.graph.Vertex.create(1));
        actual.addVertex(model.graph.Vertex.create(2));
        actual.addVertex(model.graph.Vertex.create(3));

        actual.addEdge(Edge.create(11, actual.getVertex(1), actual.getVertex(2)));
        actual.addEdge(Edge.create(23, actual.getVertex(1), actual.getVertex(3)));

        actual.removeEdge(1, 2);

        //check size
        Assert.assertEquals(1, actual.getVertex(1).getEdges().size());

        //check if we can use removed edge
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> actual.getVertex(1).getEdges().get(1));

        //check remaining edge
        Assert.assertEquals(23, actual.getVertex(1).getEdges().get(0).getWeight(), Double.MIN_VALUE);
        Assert.assertEquals(actual.getVertex(3), actual.getVertex(1).getEdges().get(0).getTargetVertex());

        //removing non existing vertices
        Assert.assertThrows(NoSuchElementException.class, () -> actual.removeEdge(111, 222));
    }
}
