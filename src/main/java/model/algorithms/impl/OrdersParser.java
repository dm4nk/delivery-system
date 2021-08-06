package model.algorithms.impl;

import com.google.gson.Gson;
import exceptions.WrongOrderFormatException;
import lombok.NonNull;
import model.algorithms.Parser;
import model.graph.Graph;
import model.schedule.Order;
import model.schedule.Schedule;
import model.schedule.impl.OrdersSchedule;
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

/**
 * Used to parse orders to schedules
 */
public class OrdersParser implements Parser {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

    static {
        formatter.setLenient(false);
    }

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
    @Override
    public <T extends Schedule> void parseTo(@NonNull File file, @NonNull T Schedule, Graph graph) throws IOException, org.json.simple.parser.ParseException, ParseException, WrongOrderFormatException {
        if (!file.getName().endsWith(".json")) throw new FileNotFoundException(file.getName() + " is not a json file");

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

            Schedule.addOrder(
                    graph,
                    Order.create(
                            (String) temp.get("id"),
                            (Double) temp.get("lat"),
                            (Double) temp.get("lon"),
                            date
                    )
            );
        }
    }
}
