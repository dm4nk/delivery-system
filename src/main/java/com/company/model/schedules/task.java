package com.company.model.schedules;

import com.company.Exceptions.wrongTaskFormatException;
import java.text.SimpleDateFormat;
import java.util.*;


public class task {
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

    public task(int from, Date fromTime, int to, Date toTime) throws wrongTaskFormatException {
        if(from < 0 || to < 0) throw new wrongTaskFormatException("negative points");
        if(fromTime.getTime() >= toTime.getTime()) throw new wrongTaskFormatException("from time less than to time");
        this.fromIndex = from;
        this.toIndex = to;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public task(String from, Date fromTime, String to, Date toTime) throws wrongTaskFormatException {
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

    public task(String from, Date fromTime, String to, Date toTime, double fromLat, double fromLen) throws wrongTaskFormatException {
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
}
