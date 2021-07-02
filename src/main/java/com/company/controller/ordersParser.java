package com.company.controller;

import com.company.model.schedules.order;
import com.company.model.schedules.ordersSchedule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ordersParser {
    public static void parseTo(File file, ordersSchedule ordersSchedule) throws IOException, org.json.simple.parser.ParseException, ParseException {
        if(!file.getName().endsWith(".json")) throw new FileNotFoundException(file.getName() + " is not a json file");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);

        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        String valuesStr = new String(bytes);

        JSONObject root = (JSONObject) new JSONParser().parse(valuesStr);

        JSONArray orders_ = (JSONArray) root.get("orders");

        JSONObject temp;
        for (Object o : orders_) {
            temp = (JSONObject) o;

            Date date;
            try {
                date = formatter.parse((String) temp.get("date"));
            } catch (ParseException e) {
                throw new ParseException("incorrect date format", 0);
            }

            ordersSchedule.addOrder(
                    (String) temp.get("id"),
                    new order(
                            date,
                            Double.parseDouble((String) temp.get("lat")),
                            Double.parseDouble((String) temp.get("lon"))
                    )
            );
        }
    }
}
