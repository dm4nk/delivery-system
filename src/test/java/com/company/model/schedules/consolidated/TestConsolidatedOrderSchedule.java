package com.company.model.schedules.consolidated;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.algorithms.OrdersParser;
import com.company.algorithms.Parser;
import com.company.model.schedules.Order;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class TestConsolidatedOrderSchedule {
    @Test
    public void testConsolidationOrAddOrder() throws WrongOrderFormatException, ParseException, org.json.simple.parser.ParseException, IOException {
        String path = "src\\test\\resources\\";

        ConsolidatedOrderSchedule actual = new ConsolidatedOrderSchedule();
        Parser parser = new OrdersParser();
        parser.parseTo(new File(path + "other\\TestConsOrders.json"), actual);

        Assert.assertEquals(3, actual.pollRoute().size());
        Assert.assertEquals(1, actual.pollRoute().size());
        Assert.assertEquals(1, actual.pollRoute().size());
        Assert.assertEquals(2, actual.pollRoute().size());
    }
}
