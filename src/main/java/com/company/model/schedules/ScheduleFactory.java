package com.company.model.schedules;

import com.company.model.schedules.consolidated.ConsolidatedOrderSchedule;
import com.company.model.schedules.raw.OrdersSchedule;

public class ScheduleFactory {
    public ScheduleType factoryType;

    private ScheduleFactory(ScheduleType factoryType){
        this.factoryType = factoryType;
    }

    public static ScheduleFactory create(ScheduleType factoryType){
        return new ScheduleFactory(factoryType);
    }

    public static ScheduleFactory create(){
        return new ScheduleFactory(ScheduleType.CONSISTENT);
    }

    public void setFactoryType(ScheduleType factoryType){
        this.factoryType = factoryType;
    }

    public Schedule createSchedule(){
        switch (factoryType){
            case CONSISTENT: return OrdersSchedule.create();
            case CONSOLIDATED: return ConsolidatedOrderSchedule.create();
            default: return null;//in case of errors
        }
    }
}
