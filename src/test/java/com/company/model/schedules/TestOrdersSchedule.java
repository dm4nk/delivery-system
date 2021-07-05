package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import org.junit.Assert;
import org.junit.Test;

public class TestOrdersSchedule {
    @Test
    public void testAddOrder() throws WrongTaskFormatException {
        OrdersSchedule actual = new OrdersSchedule();
        actual.addOrder("1", new Order(null, -1, -1));

        Assert.assertThrows(WrongTaskFormatException.class, ()->actual.addOrder("1", new Order(null, -4, -10)));
    }
}
