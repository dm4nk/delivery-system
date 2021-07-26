package model.algorithms;

import model.graph.Edge;
import model.graph.Graph;
import model.graph.Vertex;

/**
 * Has a static method to write graph into console
 */
public class GraphWriter {
    private GraphWriter() {
    }

    /**
     * writes graph into console
     */
    public static void writeGraph(Graph graph) {
        for (Vertex vertex : graph.getVertices().values())
            for (Edge edge : vertex.getEdges())
                System.out.println(
                        edge.getStartVertex().getName() + " --" + edge.getWeight() + "-> " + edge.getTargetVertex().getName()
                );
    }
}
