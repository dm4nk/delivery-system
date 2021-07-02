package com.company.model.schedules;

import com.company.model.Exceptions.wrongTaskFormatException;
import com.company.model.graph.graph;
import java.util.ArrayList;

public class tasksSchedule {
    private ArrayList<task> tasks;

    public tasksSchedule(ArrayList<task> tasks){
        this.tasks = tasks;
    }

    public tasksSchedule(){
        tasks = new ArrayList<>();
    }

    public void writeBestPath(graph graph, int indexInSchedule) throws wrongTaskFormatException {
            graph.writeBestPath(tasks.get(indexInSchedule));
    }

    public task getTask(int index){
        return  tasks.get(index);
    }

    public void addTask(task task){
        tasks.add(task);
    }

    public void setTask(int index, task task){
        tasks.set(index, task);
    }
}
