package com.company.controller;

import com.company.Exceptions.wrongGraphFormatException;
import com.company.Exceptions.wrongTaskFormatException;
import com.company.model.schedules.ordersSchedule;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TestOrdersParser {
    @Test
    public void testParseTo() throws ParseException, java.text.ParseException, IOException, wrongTaskFormatException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        String path = "src\\test\\resources\\dataset\\";

        ordersSchedule actual = new ordersSchedule();

        ordersParser.parseTo(new File(path + "testOrders.json"), actual);

        Assert.assertEquals(2, actual.size());

        Assert.assertEquals(formatter.parse("09-8-2018 15:16:03"), actual.getOrder("1").getDate());
        Assert.assertEquals(-37.8007208, actual.getOrder("1").getLat(), Double.MIN_VALUE);
        Assert.assertEquals(144.959719, actual.getOrder("1").getLon(), Double.MIN_VALUE);

        Assert.assertEquals(formatter.parse("08-02-2018 09:10:59"), actual.getOrder("2").getDate());
        Assert.assertEquals(-37.8207999, actual.getOrder("2").getLat(), Double.MIN_VALUE);
        Assert.assertEquals(144.9794147, actual.getOrder("2").getLon(), Double.MIN_VALUE);
    }
}
