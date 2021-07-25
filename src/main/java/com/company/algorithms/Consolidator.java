package com.company.algorithms;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.schedules.consolidated.ConsolidatedOrderSchedule;
import com.company.model.schedules.raw.OrdersSchedule;

public class Consolidator {
    public static ConsolidatedOrderSchedule consolidate(Graph graph, OrdersSchedule schedule) throws WrongOrderFormatException {
        return ConsolidatedOrderSchedule.createAndConsolidate(graph, schedule);
    }
}
