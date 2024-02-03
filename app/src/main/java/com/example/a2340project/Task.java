package com.example.a2340project;
import android.os.Build;

import java.time.LocalDate;
import java.util.Calendar;
import java.time.LocalDateTime;
import java.util.UUID;
public class Task {
    String title;
    LocalDateTime dueDate;
    String description;
    String uniqueID;
    String location;
    Course course;

    private boolean check = false;

    public Task(String title, LocalDateTime dueDate, String description) {
        this(title, dueDate, description, null, "");
    }
    public Task(String title, LocalDateTime dueDate, String description, Course course, String location) {
        this.description = description;
        this.dueDate = dueDate;
        this.title = title;
        this.uniqueID = UUID.randomUUID().toString();
        this.course = course;
        this.location = location;
    }


    public Task(String title, String description) {
        this(title, LocalDateTime.now(), description);

    }

    public void setCompletion(boolean status) {
        this.check = status;
    }
    public boolean getChecked() {
        return this.check;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getUniqueID() {
        return uniqueID;
    }
    public Course getCourse() {
        return course;
    }

//    public String getUIDescription() {
//
//    }

}
