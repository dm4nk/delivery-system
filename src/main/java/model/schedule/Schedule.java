package model.schedule;

import exceptions.WrongOrderFormatException;
import model.graph.Graph;
import model.graph.Vertex;

import java.text.ParseException;
import java.util.List;

/**
 * Interface which every Schedule should implement
 */
public interface Schedule {
    /**
     * @param graph graph to work with
     * @param order order to add
     * @throws WrongOrderFormatException if such order already exists
     */
    void addOrder(Graph graph, Order order) throws WrongOrderFormatException;

    /**
     * Writes all paths of this schedule to console according to its type
     *
     * @param graph        graph to work with
     * @param fromVertices vertices we can go from
     */
    void writePaths(Graph graph, List<Vertex> fromVertices) throws ParseException;
}
