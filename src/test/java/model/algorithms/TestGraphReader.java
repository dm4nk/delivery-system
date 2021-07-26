package model.algorithms;

import exceptions.WrongGraphFormatException;
import model.graph.Edge;
import model.graph.NotSingletonGraph;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestGraphReader {
    final String path = "src\\test\\resources\\";
    @Test
    public void testReadGraph1arg() throws IOException, WrongGraphFormatException {
        double[][] actual = GraphReader.readGraph(new File(path + "other/testGraph.txt"));
        double[][] expected = {{Double.MAX_VALUE,2},{4,Double.MAX_VALUE}};
        Assert.assertArrayEquals(expected, actual);

        Assert.assertThrows(IOException.class, ()-> GraphReader.readGraph(new File("notExistingFile(probably)")));
        Assert.assertThrows(WrongGraphFormatException.class, ()-> GraphReader.readGraph(new File(path + "other/testNotNXNGraph.txt")));
        Assert.assertThrows(WrongGraphFormatException.class, ()-> GraphReader.readGraph(new File(path + "other/testNegativeGraph.txt")));
    }
    @Test
    public void testReadGraph3arg() throws WrongGraphFormatException, IOException {
        NotSingletonGraph actual = NotSingletonGraph.create();
        GraphReader.readGraph(new File(path + "dataset/testNodes.csv"), new File(path + "dataset/testEdges.csv"), actual);

        NotSingletonGraph expected = NotSingletonGraph.create();
        expected.addVertex("1", 144.9749471, -37.8152153);
        expected.addVertex("2", 144.9558726, -37.807675);
        expected.addVertex("3", 144.9559785, -37.8070943);
        expected.addEdge(0.06*58/15,"1", "2");
        expected.addEdge(0.06*15/20,"1", "3");

        Assert.assertEquals(expected.size(), actual.size());

        for(String st: new String[]{"1", "2", "3"}) {
            Assert.assertEquals(expected.getVertices().get(st).getEdges().size(), actual.getVertices().get(st).getEdges().size());

            for(int i = 0;  i < expected.getVertices().get(st).getEdges().size(); ++i){
                Edge actualEdge = actual.getVertices().get(st).getEdges().get(i);
                Edge expectedEdge = expected.getVertices().get(st).getEdges().get(i);
                Assert.assertEquals(expectedEdge, actualEdge);
            }

            Assert.assertEquals(expected.getVertices().get(st), actual.getVertices().get(st));
        }

        Assert.assertThrows(FileNotFoundException.class, ()-> GraphReader.readGraph(new File(path + "dataset/testNodes.txt"), new File(path + "dataset/testEdges.csv"), actual));
        Assert.assertThrows(FileNotFoundException.class, ()-> GraphReader.readGraph(new File(path + "dataset/testNodes.csv"), new File(path + "dataset/testEdges.txt"), actual));

    }
}
