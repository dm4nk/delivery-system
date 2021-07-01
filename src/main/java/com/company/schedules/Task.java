package com.company.schedules;

import com.company.Exceptions.wrongTaskFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Task {
    static private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

    private String from;
    private String to;
    private int fromIndex;
    private int toIndex;
    private Date fromTime;
    private Date toTime;

    //Главный вопрос: будет ли это использовано?????
    private double lat;
    private double len;

    public Task(int from, Date fromTime, int to, Date toTime) throws wrongTaskFormatException {
        if(from < 0 || to < 0) throw new wrongTaskFormatException("negative points");
        if(fromTime.getTime() >= toTime.getTime()) throw new wrongTaskFormatException("from time less than to time");
        this.fromIndex = from;
        this.toIndex = to;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Task(String from, Date fromTime, String to, Date toTime) throws wrongTaskFormatException {
        if(fromTime.getTime() >= toTime.getTime()) throw new wrongTaskFormatException("from time less than to time");
        try {
            fromIndex = Integer.parseInt(from);
            toIndex = Integer.parseInt(to);
        }
        catch (Exception e){
            fromIndex = -1;
            toIndex = -1;
        }
        this.from = from;
        this.to = to;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Task(String from, Date fromTime, String to, Date toTime, double fromLat, double fromLen) throws wrongTaskFormatException {
        this(from, fromTime, to, toTime);
        this.lat = fromLat;
        this.len = fromLen;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int timeRequired(){
        return (int) ((toTime.getTime()-fromTime.getTime())/60000);
    }//условно в минутах...

    public void writeTask(){
        System.out.println(from + " " + formatter.format(fromTime) + "\n" +
                        to + " " + formatter.format(toTime)
                );
    }

    public String toString(){
        return from + " " + formatter.format(fromTime) + "\n" + to + " " + formatter.format(toTime);
    }

    public static ArrayList<Task> readTasksToArrayList(File file) throws IOException, wrongTaskFormatException, ParseException {
        //чтобы нельзя быол ввести дату типа 97.09.2021
        formatter.setLenient(false);

        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        //разбиваем строку на массив строк, в каждой их которых содержится 1 число
        String[] valueStr = new String(bytes).trim().split("\\s+");

        if(valueStr.length % 6 != 0 ) throw new wrongTaskFormatException("wrong task format");

        ArrayList<Task> tasks = new ArrayList();//

        for(int i = 0; i < valueStr.length; i+=6){
            Date dateFrom;
            Date dateTo;
            try {
                dateFrom = formatter.parse(valueStr[i + 1] + " " + valueStr[i + 2]);
                dateTo = formatter.parse(valueStr[i + 4] + " " + valueStr[i + 5]);
            }
            catch (ParseException e){
                throw new ParseException("incorrect date format", 0);
            }

            tasks.add(new Task(
                    valueStr[i],
                    dateFrom,
                    valueStr[i+3],
                    dateTo)
            );
        }
        return tasks;
    }
}
