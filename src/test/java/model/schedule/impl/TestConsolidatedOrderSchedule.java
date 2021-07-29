package model.schedule.impl;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.algorithms.Parser;
import model.algorithms.impl.OrdersParser;
import model.graph.NotSingletonGraph;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class TestConsolidatedOrderSchedule {
    @Test
    public void testConsolidationOrAddOrder() throws WrongOrderFormatException, ParseException, org.json.simple.parser.ParseException, IOException, WrongGraphFormatException {
        String path = "src\\test\\resources\\";
        NotSingletonGraph graph = NotSingletonGraph.create();
        graph.addVertex(0, 0, 0);
        graph.addVertex(1, 0.0001, 0.0001);
        graph.addVertex(2, 0.0002, 0.0002);
        graph.addVertex(3, 0.0003, 0.0003);
        graph.addVertex(4, 1, 1);
        graph.addVertex(5, 2, 2);
        graph.addVertex(6, 2.0001, 2.0001);

        graph.addEdge(5, 0, 1);
        graph.addEdge(5, 0, 2);
        graph.addEdge(5, 0, 3);
        graph.addEdge(5, 1, 2);
        graph.addEdge(1, 2, 3);

        graph.addEdge(10000, 3, 4);
        graph.addEdge(10000, 4, 5);
        graph.addEdge(5, 5, 6);

        ConsolidatedOrderSchedule actual = ConsolidatedOrderSchedule.create();
        Parser parser = new OrdersParser();
        parser.parseTo(new File(path + "dataset\\TestConsOrders.json"), actual, graph);

        Assert.assertEquals(3, actual.getRoute(0).size());
        Assert.assertEquals(1, actual.getRoute(1).size());
        Assert.assertEquals(1, actual.getRoute(2).size());
        Assert.assertEquals(2, actual.getRoute(3).size());
    }
}
