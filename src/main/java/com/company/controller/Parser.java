package com.company.controller;

import com.company.Exceptions.WrongTaskFormatException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface Parser {
    //todo: 5
    void parseTo(File file, Object Schedule) throws IOException, org.json.simple.parser.ParseException, ParseException, WrongTaskFormatException;
}
