package com.company.controller;

import com.company.model.Exceptions.wrongGraphFormatException;
import com.company.model.graph.Edge;
import com.company.model.graph.notSingletonGraph;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestGraphReader {
    String path = "C:\\Users\\dimxx\\IdeaProjects\\magenta_test\\src\\test\\resources\\";
    @Test
    public void testReadGraph1arg() throws IOException, wrongGraphFormatException {
        double[][] actual = graphReader.readGraph(path + "testGraph.txt");
        double[][] expected = {{Double.MAX_VALUE,2},{4,Double.MAX_VALUE}};
        Assert.assertArrayEquals(expected, actual);
    }
    @Test
    public void testReadGraph3arg() throws wrongGraphFormatException, IOException {
        notSingletonGraph actual = new notSingletonGraph();
        graphReader.readGraph(path + "testNodes.csv", path + "testEdges.csv", actual);

        notSingletonGraph expected = new notSingletonGraph();
        expected.addVertex("1", -37.8152153, 144.9749471);
        expected.addVertex("2", -37.807675, 144.9558726);
        expected.addVertex("3", -37.8070943, 144.9559785);
        expected.addEdge(0.06*58/15,"1", "2");
        expected.addEdge(0.06*15/20,"1", "3");

        Assert.assertEquals(expected.size(), actual.size());

        for(String st: new String[]{"1", "2", "3"}) {
            Assert.assertEquals(expected.getVertices().get(st).getEdges().size(), actual.getVertices().get(st).getEdges().size());

            for(int i = 0;  i < expected.getVertices().get(st).getEdges().size(); ++i){
                Edge actualEdge = actual.getVertices().get(st).getEdges().get(i);
                Edge expectedEdge = expected.getVertices().get(st).getEdges().get(i);
                Assert.assertEquals(expectedEdge.hashCode(), actualEdge.hashCode());
            }

            Assert.assertEquals(expected.getVertices().get(st).hashCode(), actual.getVertices().get(st).hashCode());
        }
    }
}
