package com.company.model.schedules.raw;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.schedules.Order;
import com.company.model.schedules.raw.OrdersSchedule;
import org.junit.Assert;
import org.junit.Test;

public class TestOrdersSchedule {
    @Test
    public void testAddOrder() throws WrongOrderFormatException {
        OrdersSchedule actual = new OrdersSchedule();
        actual.addOrder(new Order("1",null, -1, -1));

        Assert.assertThrows(WrongOrderFormatException.class, ()->actual.addOrder(new Order("1", null, -4, -10)));
    }
}
