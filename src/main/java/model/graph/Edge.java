package model.graph;

import exceptions.WrongGraphFormatException;
import lombok.Data;
import lombok.NonNull;

/**
 * Represents an edge between 2 vertices in graph
 */
@Data()
public class Edge {
    @NonNull
    private double weight;
    @NonNull
    private Vertex startVertex;
    @NonNull
    private Vertex targetVertex;

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
