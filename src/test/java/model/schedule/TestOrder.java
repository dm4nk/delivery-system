package model.schedule;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.graph.NotSingletonGraph;
import model.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

public class TestOrder {
    @Test
    public  void testCalculateNearestVertex() throws WrongGraphFormatException, WrongOrderFormatException {
        NotSingletonGraph graph = NotSingletonGraph.create();

        graph.addVertex("1", 53.253709, 50.209983);
        graph.addVertex("2", 53.256407, 50.212732);
        Order order = new Order("",null, 53.252608, 50.210996);
        Order wrong = new Order("", null, 53.253554, 50.200895);

        order.setNearestVertex(graph);
        Vertex actual = order.getVertex();

        Assert.assertEquals("1", actual.getName());
        Assert.assertThrows(WrongOrderFormatException.class, () -> wrong.setNearestVertex(graph));
    }
}