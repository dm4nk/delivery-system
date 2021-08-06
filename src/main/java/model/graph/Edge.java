package model.graph;

import exceptions.WrongGraphFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * Represents an edge between 2 vertices in graph
 */
@EqualsAndHashCode
public class Edge {
    @Getter
    private final double weight;
    @Getter
    private final Vertex startVertex;
    @Getter
    private final Vertex targetVertex;

    private Edge(double weight, Vertex startVertex, Vertex targetVertex) {
        this.weight = weight;
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
    }

    /**
     * @param weight       weight of an edge
     * @param sourceVertex dispatch vertex
     * @param targetVertex arrival vertex
     * @throws WrongGraphFormatException weight < 0 or source and target vertices are the same
     */
    public static Edge create(double weight, @NonNull Vertex sourceVertex, @NonNull Vertex targetVertex) throws WrongGraphFormatException {
        if (sourceVertex.equals(targetVertex))
            throw new WrongGraphFormatException("source vertex equals to target vertex");
        if (weight < 0) throw new WrongGraphFormatException("negative weight");

        return new Edge(weight, sourceVertex, targetVertex);
    }
}