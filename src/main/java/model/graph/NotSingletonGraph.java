package model.graph;

/**
 * Used for tests to avoid affecting main graph
 */
public class NotSingletonGraph extends Graph {
    private NotSingletonGraph() {
    }

    public static NotSingletonGraph create() {
        return new NotSingletonGraph();
    }
}