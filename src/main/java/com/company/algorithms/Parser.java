package com.company.algorithms;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.schedules.Schedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface Parser {
    <T extends Schedule> void parseTo(File file, T Schedule, Graph graph) throws IOException, org.json.simple.parser.ParseException, ParseException, WrongOrderFormatException;
}
