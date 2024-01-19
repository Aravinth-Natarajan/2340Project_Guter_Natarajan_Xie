package com.example.a2340project;
import java.util.Date;
import java.util.UUID;
public class Task {
    String title;
    Date dueDate;
    String description;
    String uniqueID;

    public Task(String title, Date dueDate, String description) {
        this.description = description;
        this.dueDate = dueDate;
        this.title = title;
        this.uniqueID = UUID.randomUUID().toString();
    }
    public Task(String title, String description) {
        this(title, null, description);
    }
}
