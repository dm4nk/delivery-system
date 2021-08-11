package model.algorithms.impl;

import exceptions.WrongGraphFormatException;
import exceptions.WrongOrderFormatException;
import model.algorithms.OrdersParser;
import model.dto.DTO;
import model.graph.NotSingletonGraph;
import model.graph.Vertex;
import model.schedule.impl.ConsistentOrdersSchedule;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TestOrdersParser {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

    static {
        formatter.setLenient(false);
    }

    @Test
    public void testParseTo() throws IOException, WrongOrderFormatException, WrongGraphFormatException, ParseException {
        String path = "src\\test\\resources\\dataset\\";

        NotSingletonGraph graph = NotSingletonGraph.create();
        graph.addVertex(Vertex.create(1, -37.8007208, 144.959719));
        graph.addVertex(Vertex.create(2, -37.8207999, 144.9794147));

        ConsistentOrdersSchedule actual = ConsistentOrdersSchedule.create();

        //parsing info to actual ordersSchedule
        List<DTO.order> lst = OrdersParser.parse(new File(path + "testOrders.json"));
        actual.readFromDTO(graph, lst);

        Assert.assertEquals(2, actual.size());

        //check all orders' fields
        Assert.assertEquals(formatter.parse("09-8-2018 15:16:03"), actual.getOrder("1").getDispatchTime());
        Assert.assertEquals(-37.8007208, actual.getOrder("1").getLat(), Double.MIN_VALUE);
        Assert.assertEquals(144.959719, actual.getOrder("1").getLon(), Double.MIN_VALUE);

        Assert.assertEquals(formatter.parse("08-02-2018 09:10:59"), actual.getOrder("2").getDispatchTime());
        Assert.assertEquals(-37.8207999, actual.getOrder("2").getLat(), Double.MIN_VALUE);
        Assert.assertEquals(144.9794147, actual.getOrder("2").getLon(), Double.MIN_VALUE);
    }
}
