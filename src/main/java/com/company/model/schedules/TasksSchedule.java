package com.company.model.schedules;

import com.company.Exceptions.WrongTaskFormatException;
import com.company.model.graph.Graph;

import java.util.ArrayList;

public class TasksSchedule {
    private ArrayList<Task> tasks;

    public TasksSchedule(ArrayList<Task> tasks){
        this.tasks = tasks;
    }

    public TasksSchedule(){
        tasks = new ArrayList<>();
    }

    public void writeBestPath(Graph graph, int indexInSchedule) throws WrongTaskFormatException {
            graph.writeBestPath(tasks.get(indexInSchedule));
    }

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
