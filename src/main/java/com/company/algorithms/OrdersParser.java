package com.company.algorithms;

import com.company.Exceptions.WrongOrderFormatException;
import com.company.model.graph.Graph;
import com.company.model.schedules.Order;
import com.company.model.schedules.Schedule;
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

public class OrdersParser implements Parser {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
    static {formatter.setLenient(false);}

    @Override
    public<T extends Schedule> void parseTo(File file, T schedule, Graph graph) throws IOException, org.json.simple.parser.ParseException, ParseException, WrongOrderFormatException {
        if(!file.getName().endsWith(".json")) throw new FileNotFoundException(file.getName() + " is not a json file");

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

            schedule.addOrder(
                    graph,
                    new Order(
                            (String) temp.get("id"),
                            date,
                            (Double) temp.get("lon"),
                            (Double) temp.get("lat")
                    )
            );
        }
    }
}
