package model.algorithms;

import exceptions.WrongOrderFormatException;
import lombok.NonNull;
import model.graph.Graph;
import model.schedule.impl.ConsolidatedOrderSchedule;
import model.schedule.impl.ConsistentOrdersSchedule;

/**
 * Contains static method for OrderSchedule consolidation
 */
public class Consolidator {
    private Consolidator() {

    }

    /**
     * turns OrderSchedule() to ConsolidatedOrdersSchedule()
     *
     * @param graph    - graph used
     * @param schedule - schedule to convert
     * @return new ConsolidatedOrderSchedule
     * @throws WrongOrderFormatException if somehow error occurred while adding orders from old schedule to a new one
     */
    public static ConsolidatedOrderSchedule consolidate(Graph graph, @NonNull ConsistentOrdersSchedule schedule) throws WrongOrderFormatException {
        return ConsolidatedOrderSchedule.createAndConsolidate(graph, schedule);
    }
}
