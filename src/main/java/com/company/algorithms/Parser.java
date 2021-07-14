package com.company.algorithms;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.schedules.Schedule;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface Parser {
    <T extends Schedule> void parseTo(File file, T Schedule) throws IOException, org.json.simple.parser.ParseException, ParseException, WrongTaskFormatException;
}
