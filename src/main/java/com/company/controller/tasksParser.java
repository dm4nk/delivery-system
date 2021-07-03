package com.company.controller;

import com.company.Exceptions.wrongTaskFormatException;
import com.company.model.schedules.task;
import com.company.model.schedules.tasksSchedule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class tasksParser {
    public static void parseTo(File file, tasksSchedule tasksSchedule) throws IOException, org.json.simple.parser.ParseException, ParseException, wrongTaskFormatException {
        if(!file.getName().endsWith(".json")) throw new FileNotFoundException("can not work with files except txt and json");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);

        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        String valuseStr = new String(bytes);

        JSONObject root = (JSONObject) new JSONParser().parse(valuseStr);

        JSONArray tasks = (JSONArray) root.get("tasks");

        JSONObject temp;
        for (Object task : tasks) {
            temp = (JSONObject) task;

            Date dateFrom;
            Date dateTo;
            try {
                dateFrom = formatter.parse((String) temp.get("fromTime"));
                dateTo = formatter.parse((String) temp.get("toTime"));
            } catch (ParseException e) {
                throw new ParseException("incorrect date format", 0);
            }

            tasksSchedule.addTask(
                    new task(
                            (String) temp.get("from"),
                            dateFrom,
                            (String) temp.get("to"),
                            dateTo
                    )
            );
        }
    }

}
