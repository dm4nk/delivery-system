package model.graph;

import lombok.Data;
import lombok.NonNull;

/**
 * Represents an edge between 2 vertices in graph
 */
@Data(staticConstructor = "create")
public class Edge {
    @NonNull
    private double weight;
    @NonNull
    private Vertex startVertex;
    @NonNull
    private Vertex targetVertex;
}
