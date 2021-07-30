package model.algorithms;

import exceptions.WrongGraphFormatException;
import model.graph.Edge;
import model.graph.NotSingletonGraph;
import model.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestGraphReader {
    private static final String path = "src\\test\\resources\\";

    @Test
    public void testReadGraph1arg() throws IOException, WrongGraphFormatException {
        double[][] actual = GraphReader.readGraph(new File(path + "other/testGraph.txt"));
        double[][] expected = {{Double.MAX_VALUE, 2}, {4, Double.MAX_VALUE}};
        Assert.assertArrayEquals(expected, actual);

        Assert.assertThrows(IOException.class, () -> GraphReader.readGraph(new File("notExistingFile(probably)")));
        Assert.assertThrows(WrongGraphFormatException.class, () -> GraphReader.readGraph(new File(path + "other/testNotNXNGraph.txt")));
        Assert.assertThrows(WrongGraphFormatException.class, () -> GraphReader.readGraph(new File(path + "other/testNegativeGraph.txt")));
    }

    @Test
    public void testReadGraph3arg() throws WrongGraphFormatException, IOException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        GraphReader.readGraph(new File(path + "dataset/testNodes.csv"), new File(path + "dataset/testEdges.csv"), actual);

        //making graph and compare it with read one
        NotSingletonGraph expected = NotSingletonGraph.create();
        expected.addVertex(Vertex.create(1, -37.8152153, 144.9749471));
        expected.addVertex(Vertex.create(2, -37.807675, 144.9558726));
        expected.addVertex(Vertex.create(3, -37.8070943, 144.9559785));
        expected.addEdge(Edge.create(0.06 * 58 / 15, expected.getVertex(1), expected.getVertex(2)));
        expected.addEdge(Edge.create(0.06 * 15 / 20, expected.getVertex(1), expected.getVertex(3)));

        Assert.assertEquals(expected.size(), actual.size());

        for (long st : new Long[]{1L, 2L, 3L}) {
            Assert.assertEquals(expected.getVertex(st).getEdges().size(), actual.getVertex(st).getEdges().size());

            for (int i = 0; i < expected.getVertex(st).getEdges().size(); ++i) {
                Edge actualEdge = actual.getVertex(st).getEdges().get(i);
                Edge expectedEdge = expected.getVertex(st).getEdges().get(i);
                Assert.assertEquals(expectedEdge, actualEdge);
            }

            Assert.assertEquals(expected.getVertex(st), actual.getVertex(st));
        }

        Assert.assertThrows(FileNotFoundException.class, () -> GraphReader.readGraph(new File(path + "dataset/testNodes.txt"), new File(path + "dataset/testEdges.csv"), actual));
        Assert.assertThrows(FileNotFoundException.class, () -> GraphReader.readGraph(new File(path + "dataset/testNodes.csv"), new File(path + "dataset/testEdges.txt"), actual));

    }
}
