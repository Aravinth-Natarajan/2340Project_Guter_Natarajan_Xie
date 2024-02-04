package com.example.a2340project;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private List<Task> tasks;
    public ToDoList() {
        this.tasks = new ArrayList<>();
    }
    public void addTask(Task taskToAdd) {
        tasks.add(taskToAdd);
    }
    public void removeTask(Task taskToRemove) {
        if (tasks.size() == 0) {
            return;
        }
        for(int i = 0; i < tasks.size(); i++) {
            if (taskToRemove.uniqueID.equals(tasks.get(i).uniqueID)) {
                tasks.remove(i);
                break;
            }
        }
    }
    public List<Task> returnList() {
        return tasks;
    }


    public List<Task> returnCompleteTasks() {
        List<Task> incompleteTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (!task.getChecked()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

    public List<Task> retur() {
        List<Task> incompleteTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (!task.getChecked()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }
     //Implement sorting based on date
}
