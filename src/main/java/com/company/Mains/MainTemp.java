package com.company.Mains;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.Exceptions.WrongTaskFormatException;
import com.company.algorithms.OrdersParser;
import com.company.algorithms.Parser;
import com.company.model.graph.Graph;
import com.company.model.schedules.OrdersSchedule;
import com.company.model.schedules.consolidated.ConsolidatedOrderSchedule;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class MainTemp {
    public static void main(String[] args) throws WrongGraphFormatException, IOException, ParseException, java.text.ParseException, WrongTaskFormatException {

        String path = "src\\main\\resources\\";
        Graph.getInstance().readGraphFromFile(path + "dataset\\nodes.csv", path + "dataset\\edges.csv");

        OrdersSchedule ordersSchedule = new OrdersSchedule();
        Parser parser = new OrdersParser();
        parser.parseTo(new File(path + "other\\consOrders.json"), ordersSchedule);

        ConsolidatedOrderSchedule consolidatedOrderSchedule = new ConsolidatedOrderSchedule(ordersSchedule);
        consolidatedOrderSchedule.writeOrders();
    }
}