package model.algorithms;

import exceptions.WrongGraphFormatException;
import model.graph.Edge;
import model.graph.NotSingletonGraph;
import model.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDijkstra {

    //creating graph for further testing
    private static final NotSingletonGraph graph = NotSingletonGraph.create();

    static {
        try {
            graph.addAllVertices(List.of(
                    Vertex.create(1, 53.253709, 50.209983),
                    Vertex.create(2, 53.256407, 50.212732),
                    Vertex.create(3),
                    Vertex.create(4),
                    Vertex.create(5)
            ));

            graph.addAllEdges(List.of(
                    Edge.create(11, graph.getVertex(1), graph.getVertex(2)),
                    Edge.create(29, graph.getVertex(1), graph.getVertex(4)),
                    Edge.create(13, graph.getVertex(1), graph.getVertex(4)),
                    Edge.create(4, graph.getVertex(2), graph.getVertex(3)),
                    Edge.create(5, graph.getVertex(3), graph.getVertex(4)),
                    Edge.create(38, graph.getVertex(4), graph.getVertex(5)),
                    Edge.create(8, graph.getVertex(3), graph.getVertex(5))
            ));
        } catch (WrongGraphFormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testComputePath() {
        Dijkstra.computePath(graph.getVertex(1));

        Assert.assertEquals(0, graph.getVertex(1).getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(11, graph.getVertex(2).getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(15, graph.getVertex(3).getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(13, graph.getVertex(4).getMinDistance(), Double.MIN_VALUE);
        Assert.assertEquals(23, graph.getVertex(5).getMinDistance(), Double.MIN_VALUE);

        graph.validate();
    }

    @Test
    public void testGetShortestPathTo() {

        Dijkstra.computePath(graph.getVertex(1));
        List<Vertex> actual = Dijkstra.getShortestPathTo(graph.getVertex(5));

        List<Vertex> expected = new ArrayList<>();
        expected.add(graph.getVertex(5));
        expected.add(graph.getVertex(3));
        expected.add(graph.getVertex(2));
        expected.add(graph.getVertex(1));

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

        Assert.assertThrows(NullPointerException.class, () -> Dijkstra.computePath(null));
        Assert.assertThrows(NullPointerException.class, () -> Dijkstra.getShortestPathTo(null));

        graph.validate();
    }

    @Test
    public void testCalculateNearestVertex() {
        Vertex actual = Dijkstra.calculateNearestVertexFromLatLon(graph, 53.252608, 50.210996);

        Assert.assertEquals(1, actual.getId());
        Assert.assertNull(Dijkstra.calculateNearestVertexFromLatLon(graph, 53.253554, 50.200895));
    }
}
