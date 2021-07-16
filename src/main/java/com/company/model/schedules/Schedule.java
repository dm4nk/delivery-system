package com.company.model.schedules;

import com.company.Exceptions.WrongOrderFormatException;

public interface Schedule {
    void addOrder(Order order) throws WrongOrderFormatException;
}
