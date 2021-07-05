package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Task {
    //TODO: переопределить equals() and hashCode()
    static private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

    private String from;
    private String to;
    private int fromIndex;
    private int toIndex;
    private Date fromTime;
    private Date toTime;

    private double lat;
    private double len;

    public Task(int from, Date fromTime, int to, Date toTime) throws WrongTaskFormatException {
        if(from < 0 || to < 0) throw new WrongTaskFormatException("negative points");
        if(fromTime.getTime() >= toTime.getTime()) throw new WrongTaskFormatException("from time less than to time");
        this.fromIndex = from;
        this.toIndex = to;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Task(String from, Date fromTime, String to, Date toTime) throws WrongTaskFormatException {
        if(fromTime.getTime() >= toTime.getTime()) throw new WrongTaskFormatException("from time less than to time");
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

    public Task(String from, Date fromTime, String to, Date toTime, double fromLat, double fromLen) throws WrongTaskFormatException {
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

    @Override
    public String toString(){
        return from + " " + formatter.format(fromTime) + "\n" + to + " " + formatter.format(toTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return fromIndex == task.fromIndex &&
                toIndex == task.toIndex &&
                Double.compare(task.lat, lat) == 0 &&
                Double.compare(task.len, len) == 0 &&
                from.equals(task.from) &&
                to.equals(task.to) &&
                fromTime.equals(task.fromTime) &&
                toTime.equals(task.toTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, fromIndex, toIndex, fromTime, toTime, lat, len);
    }
}
