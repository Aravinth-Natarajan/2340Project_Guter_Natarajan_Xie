package com.example.a2340project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

    public void sortByCourse() {
        Collections.sort(tasks, new TaskClassComparator());
    }

    public void sortByDate() {
        Collections.sort(tasks, new TaskDateComparator());
    }

    public List<Task> returnCompleteTasks() {
        List<Task> incompleteTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.getChecked()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

    public List<Task> returnInCompleteTasks() {
        List<Task> incompleteTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (!task.getChecked()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

    public List<Task> returnTask(boolean sortCourse, boolean sortDate, boolean complete, boolean incomplete) {
        if (sortCourse)
            sortByCourse();
        if (sortDate)
            sortByDate();
        if (complete && incomplete)
            return tasks;
        else if (complete)
            return returnCompleteTasks();
        else if (incomplete)
            return returnInCompleteTasks();
        else
            return tasks;
    }
     //Implement sorting based on date
}
