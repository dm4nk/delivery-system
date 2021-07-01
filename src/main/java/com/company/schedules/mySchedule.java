package com.company.schedules;

import com.company.Exceptions.wrongTaskFormatException;
import com.company.graph.graph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class mySchedule {
    private ArrayList<Task> tasks;

    private mySchedule(ArrayList<Task> tasks){
        this.tasks = tasks;
    }

    public static mySchedule createScheduleFromFile(File file) throws ParseException, wrongTaskFormatException, IOException, org.json.simple.parser.ParseException {
        if(file.getName().endsWith(".txt")) return createScheduleFromTXTFile(file);
        if(file.getName().endsWith(".json")) return createScheduleFromJSONFile(file);

        else throw new FileNotFoundException("can not work with files except txt and json");
    }

    private static mySchedule createScheduleFromTXTFile(File file) throws ParseException, wrongTaskFormatException, IOException {
        return new mySchedule(Task.readTasksToArrayList(file));
    }

    private static mySchedule createScheduleFromJSONFile(File file) throws IOException, org.json.simple.parser.ParseException, ParseException, wrongTaskFormatException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setLenient(false);

        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        String valuseStr = new String(bytes);

        JSONObject root = (JSONObject) new JSONParser().parse(valuseStr);

        JSONArray tasks = (JSONArray) root.get("tasks");

        ArrayList<Task> arrayListOfTasks = new ArrayList<>();
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

            arrayListOfTasks.add(
                    new Task(
                            (String) temp.get("from"),
                            dateFrom,
                            (String) temp.get("to"),
                            dateTo
                    )
            );

        }

        return new mySchedule(arrayListOfTasks);
    }

    public void writeBestPath(graph graph, int indexInSchedule) throws wrongTaskFormatException {
            graph.writeBestPath(tasks.get(indexInSchedule));
    }

//    public void write2BestPaths(graph graph, int indexInScedule) throws wrongTaskFormatException {
//        graph.write2BestPaths(tasks.get(indexInScedule));
//    }

    public Task getTask(int index){
        return  tasks.get(index);
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void setTask(int index, Task task){
        tasks.set(index, task);
    }
}
