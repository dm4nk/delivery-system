package com.company.algorithms;

import com.company.model.schedules.consolidated.ConsolidatedOrderSchedule;
import com.company.model.schedules.raw.OrdersSchedule;

public class Consolidator {
    public static ConsolidatedOrderSchedule consolidate(OrdersSchedule schedule){
        return  new ConsolidatedOrderSchedule(schedule);
    }
}
