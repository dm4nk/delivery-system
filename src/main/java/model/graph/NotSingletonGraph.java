package model.graph;

import lombok.NoArgsConstructor;

/**
 * Used for tests to avoid affecting main graph
 */
@NoArgsConstructor
public class NotSingletonGraph extends Graph {
    public static NotSingletonGraph create() {
        return new NotSingletonGraph();
    }
}