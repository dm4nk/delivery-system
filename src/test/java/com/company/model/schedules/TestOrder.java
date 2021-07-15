package com.company.model.schedules;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.model.graph.NotSingletonGraph;
import com.company.model.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

public class TestOrder {
    @Test
    public  void testCalculateNearestVertex() throws WrongGraphFormatException {
        NotSingletonGraph graph = new NotSingletonGraph();

        graph.addVertex("1", 53.253709, 50.209983);
        graph.addVertex("2", 53.256407, 50.212732);
        Order order = new Order("",null, 53.252608, 50.210996);
        Order wrong = new Order("", null, 53.253554, 50.200895);

        Vertex actual = order.calculateNearestVertex(graph);

        Assert.assertEquals("1", actual.getName());
        Assert.assertNull(wrong.calculateNearestVertex(graph));
    }
}