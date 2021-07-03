package com.company.model.schedules;

import com.company.Exceptions.wrongTaskFormatException;
import org.junit.Assert;
import org.junit.Test;

public class TestOrdersSchedule {
    @Test
    public void testAddOrder() throws wrongTaskFormatException {
        ordersSchedule actual = new ordersSchedule();
        actual.addOrder("1", new order(null, -1, -1));

        Assert.assertThrows(wrongTaskFormatException.class, ()->actual.addOrder("1", new order(null, -4, -10)));
    }
}
