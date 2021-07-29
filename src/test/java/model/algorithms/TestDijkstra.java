package model.algorithms;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.graph.NotSingletonGraph;
import model.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDijkstra {
    @Test
    public void testComputePath() throws WrongGraphFormatException {
        NotSingletonGraph graph = NotSingletonGraph.create();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(11, 1, 2);
        graph.addEdge(29, 1, 4);
        graph.addEdge(13, 1, 4);
        graph.addEdge(4, 2, 3);
        graph.addEdge(5, 3, 4);


        Dijkstra.computePath(graph.getVertex(1));


        Assert.assertEquals(0, graph.getVertex(1).getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(11, graph.getVertex(2).getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(15, graph.getVertex(3).getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(13, graph.getVertex(4).getMinDistance(), Double.MIN_VALUE);
    }

    @Test
    public void testGetShortestPathTo() throws WrongGraphFormatException {
        NotSingletonGraph graph = NotSingletonGraph.create();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(99, 1, 4);
        graph.addEdge(1, 1, 2);
        graph.addEdge(9, 1, 3);
        graph.addEdge(5, 2, 3);
        graph.addEdge(16, 3, 4);

        Dijkstra.computePath(graph.getVertex(1));
        List<Vertex> actual = Dijkstra.getShortestPathTo(graph.getVertex(4));

        List<Vertex> expected = new ArrayList<>();
        expected.add(graph.getVertex(4));
        expected.add(graph.getVertex(3));
        expected.add(graph.getVertex(2));
        expected.add(graph.getVertex(1));

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        Assert.assertThrows(NullPointerException.class, () -> Dijkstra.computePath(null));
        Assert.assertThrows(NullPointerException.class, () -> Dijkstra.getShortestPathTo(null));
    }

    @Test
    public void testCalculateNearestVertex() throws WrongGraphFormatException {
        NotSingletonGraph graph = NotSingletonGraph.create();

        graph.addVertex(1, 53.253709, 50.209983);
        graph.addVertex(2, 53.256407, 50.212732);

        Vertex actual = Dijkstra.calculateNearestVertexFromLatLon(graph, 53.252608, 50.210996);

        Assert.assertEquals(1, actual.getId());
        Assert.assertNull(Dijkstra.calculateNearestVertexFromLatLon(graph, 53.253554, 50.200895));
    }
}
