package model.schedule;

import exceptions.WrongOrderFormatException;
import model.dto.DTO;
import model.graph.Graph;
import model.graph.Vertex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

    /**
     * Fills schedule with information from list of DTOs.
     *
     * @param graph     graph to work with
     * @param orderDTOs list of order DTOs
     * @throws WrongOrderFormatException if list contains orders with the same id
     * @throws ParseException            if list contains order with invalid date
     */
    default void readFromDTO(Graph graph, List<DTO.order> orderDTOs) throws WrongOrderFormatException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);
        for (DTO.order o : orderDTOs) {
            addOrder(graph, Order.create(o.getId(), o.getLat(), o.getLon(), formatter.parse(o.getDate())));
        }
    }
}
