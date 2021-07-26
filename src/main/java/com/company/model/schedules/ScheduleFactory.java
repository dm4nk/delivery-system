package com.company.model.schedules;

import com.company.model.schedules.consolidated.ConsolidatedOrderSchedule;
import com.company.model.schedules.raw.OrdersSchedule;

public class ScheduleFactory {
    public FactoryType factoryType;

    private ScheduleFactory(FactoryType factoryType){
        this.factoryType = factoryType;
    }

    public static ScheduleFactory create(FactoryType factoryType){
        return new ScheduleFactory(factoryType);
    }

    public static ScheduleFactory create(){
        return new ScheduleFactory(FactoryType.CONSISTENT);
    }

    public void setFactoryType(FactoryType factoryType){
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
