package com.company.schedules;

import com.company.Exceptions.wrongTaskFormatException;
import com.company.graphs.graph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ordersSchedule {
    Map orders = new HashMap();
    ArrayList<String> orderIDs;

    private ordersSchedule(Map orders, ArrayList<String> orderIDs){
        this.orders = orders;
        this.orderIDs = orderIDs;
    }

    public static ordersSchedule createOrdersFromJSONFile(File file) throws IOException, org.json.simple.parser.ParseException, ParseException {
        if(!file.getName().endsWith(".json")) throw new FileNotFoundException(file.getName() + "is not a json file");

        Map orders = new HashMap();
        ArrayList<String> orderIDs = new ArrayList<>();

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
        for(int i = 0; i < orders_.size(); ++i){
            temp = (JSONObject) orders_.get(i);

            Date date;
            try {
                date = formatter.parse((String) temp.get("date"));
            }
            catch (ParseException e){
                throw new ParseException("incorrect date format", 0);
            }
            String tmpStr =  (String)temp.get("id");
            orders.put(
                    tmpStr,
                    new order(
                            date,
                            Double.parseDouble((String) temp.get("lat")),
                            Double.parseDouble((String) temp.get("lon"))
                    )
            );
            orderIDs.add(tmpStr);

        }
        return new ordersSchedule(orders, orderIDs);
    }

    public void writeBestPathFor(graph graph, order order, String restaurantStreetID) throws wrongTaskFormatException {
        order.writePathAndTime(graph, restaurantStreetID);
    }

    public order getOrder(String orderID){
        return (order) orders.get(orderID);
    }

    public order getOrder(int orderNumber){
        return (order) orders.get(orderIDs.get(orderNumber));
    }
}
