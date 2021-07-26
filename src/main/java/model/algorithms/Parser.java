package model.algorithms;

import exceptions.WrongOrderFormatException;
import model.graph.Graph;
import model.schedule.Schedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Interface which every parser to schedules should implement
 */
public interface Parser {
    /**
     * parse info from file to schedule
     *
     * @param file     file to read from
     * @param Schedule Schedule to pars to
     * @param graph    every parsing foes according to graph
     * @param <T>      extends Schedule
     * @throws IOException                           error opening file
     * @throws org.json.simple.parser.ParseException error parsing json file
     * @throws ParseException                        error parsing simple type
     * @throws WrongOrderFormatException             contains duplicates
     */
    <T extends Schedule> void parseTo(File file, T Schedule, Graph graph) throws IOException, org.json.simple.parser.ParseException, ParseException, WrongOrderFormatException;
}
