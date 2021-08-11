package model.schedule.impl;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.graph.NotSingletonGraph;
import model.graph.Vertex;
import model.schedule.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class TestConsistentOrdersSchedule {
    @Test
    public void testAddOrder() throws WrongOrderFormatException, WrongGraphFormatException {
        ConsistentOrdersSchedule actual = ConsistentOrdersSchedule.create();
        NotSingletonGraph graph = NotSingletonGraph.create();
        graph.addVertex(Vertex.create(1, -1, -1));
        graph.addVertex(Vertex.create(2, -4, -10));

        actual.addOrder(graph, Order.create("1", -1, -1, new Date()));

        Assert.assertThrows(WrongOrderFormatException.class, () -> actual.addOrder(graph, Order.create("1", -4, -10, new Date())));
        Assert.assertThrows(WrongOrderFormatException.class, () -> actual.addOrder(graph, Order.create("2", -4, -100, new Date())));
    }
}
