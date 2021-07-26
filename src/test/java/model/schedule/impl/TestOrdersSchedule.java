package model.schedule.impl;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.graph.NotSingletonGraph;
import model.schedule.Order;
import model.schedule.impl.OrdersSchedule;
import org.junit.Assert;
import org.junit.Test;

public class TestOrdersSchedule {
    @Test
    public void testAddOrder() throws WrongOrderFormatException, WrongGraphFormatException {
        OrdersSchedule actual = OrdersSchedule.create();
        NotSingletonGraph graph = NotSingletonGraph.create();
        graph.addVertex("1", -1, -1);
        graph.addVertex("2", -4, -10);

        actual.addOrder(graph, new Order("1",null, -1, -1));

        Assert.assertThrows(WrongOrderFormatException.class, ()->actual.addOrder(graph, new Order("1", null, -4, -10)));
        Assert.assertThrows(WrongOrderFormatException.class, ()->actual.addOrder(graph, new Order("2", null, -4, -100)));
    }
}
