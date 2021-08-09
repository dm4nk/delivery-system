package model.schedule.impl;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.algorithms.OrdersParser;
import model.dto.DTO;
import model.graph.Edge;
import model.graph.NotSingletonGraph;
import model.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class TestConsolidatedOrderSchedule {

    private static final String path = "src\\test\\resources\\";
    private static final NotSingletonGraph graph = NotSingletonGraph.create();

    static {
        try {
            graph.addAllVertices(List.of(
                    Vertex.create(0, 0, 0),
                    Vertex.create(1, 0.0001, 0.0001),
                    Vertex.create(2, 0.0002, 0.0002),
                    Vertex.create(3, 0.0003, 0.0003),
                    Vertex.create(4, 1, 1),
                    Vertex.create(5, 2, 2),
                    Vertex.create(6, 2.0001, 2.0001)
            ));

            graph.addAllEdges(List.of(
                    Edge.create(5, graph.getVertex(0), graph.getVertex(1)),
                    Edge.create(5, graph.getVertex(0), graph.getVertex(3)),
                    Edge.create(5, graph.getVertex(1), graph.getVertex(2)),
                    Edge.create(1, graph.getVertex(2), graph.getVertex(3)),

                    Edge.create(10_000, graph.getVertex(3), graph.getVertex(4)),
                    Edge.create(10_000, graph.getVertex(4), graph.getVertex(5)),
                    Edge.create(5, graph.getVertex(5), graph.getVertex(6))
            ));
        } catch (WrongGraphFormatException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testConsolidationOrAddOrder() throws WrongOrderFormatException, ParseException, IOException {

        ConsolidatedOrderSchedule actual = ConsolidatedOrderSchedule.create();

        //let's say that parser is working ok
        List<DTO.order> lst = OrdersParser.parse(new File(path + "dataset\\TestConsOrders.json"));

        //parsing also uses AddOrder
        actual.readFromDTO(graph, lst);

        //check size of the routes according ro graph
        Assert.assertEquals(3, actual.getRoute(0).size());
        Assert.assertEquals(1, actual.getRoute(1).size());
        Assert.assertEquals(1, actual.getRoute(2).size());
        Assert.assertEquals(2, actual.getRoute(3).size());
    }
}
