package model.schedule;

import model.schedule.impl.ConsolidatedOrderSchedule;
import model.schedule.impl.OrdersSchedule;

/**
 * Factory to create schedules
 */
public class ScheduleFactory {
    public FactoryType factoryType;

    private ScheduleFactory(FactoryType factoryType) {
        this.factoryType = factoryType;
    }

    public static ScheduleFactory create(FactoryType factoryType) {
        return new ScheduleFactory(factoryType);
    }

    /**
     * Creates a default schedule factory with consistent type
     */
    public static ScheduleFactory create() {
        return new ScheduleFactory(FactoryType.CONSISTENT);
    }

    public void setFactoryType(FactoryType factoryType) {
        this.factoryType = factoryType;
    }

    public Schedule createSchedule() {
        switch (factoryType) {
            case CONSISTENT:
                return OrdersSchedule.create();
            case CONSOLIDATED:
                return ConsolidatedOrderSchedule.create();
            default:
                return null;//in case of errors
        }
    }
}
