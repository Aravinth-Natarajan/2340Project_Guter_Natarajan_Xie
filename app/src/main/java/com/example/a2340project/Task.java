package com.example.a2340project;
import android.os.Build;

import java.time.LocalDate;
import java.util.Calendar;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Date;
import java.util.Calendar;
public class Task {
    String title;
    Calendar dueDate;
    String description;
    String uniqueID;
    String location;
    Course course;
    final int YEAR = 2024;

    private boolean check = false;

    public Task(String title, Calendar dueDate, String description) {
        this(title, dueDate, description, null, "");
    }
    public Task(String title, Calendar dueDate, String description, Course course, String location) {
        this.description = description;
        this.dueDate = dueDate;
        this.title = title;
        this.uniqueID = UUID.randomUUID().toString();
        this.course = course;
        this.location = location;
    }
    public Task(String title, Calendar dueDate, String description, Course course) {
        this(title, dueDate, description, course, "");
    }

    public Task(String title, int month, int date, int hourOfDay, int minute, String description, Course course, String location) {
        this(title, Calendar.getInstance(), description, course, location);
        dueDate.set(YEAR, month, date, hourOfDay, minute);
    }

    public Task(String title, int year, int month, int date, int hourOfDay, int minute, String description, Course course, String location) {
        this(title, Calendar.getInstance(), description, course, location);
        dueDate.set(year, month, date, hourOfDay, minute);
    }
    public Task(String title, String description) {
        this(title, Calendar.getInstance(), description);

    }

    public void setCompletion(boolean status) {
        this.check = status;
    }

    public void setCheck() {
        this.check = !this.check;
    }
    public boolean getChecked() {
        return this.check;
    }

    public String getTitle() {
        return title;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public int getYear() {
        return dueDate.get(Calendar.YEAR);
    }

    public int getMonth() {
        return dueDate.get(Calendar.MONTH);
    }

    public int getDate() {
        return dueDate.get(Calendar.DATE);
    }

    public void setDueDate(int year, int month, int date, int hourOfDay, int minute) {
        dueDate.set(year, month, date, hourOfDay, minute);
    }

    public void setDueDate(int month, int date, int hourOfDay, int minute) {
        dueDate.set(YEAR, month, date, hourOfDay, minute);
    }


    public String getTime() {
        return String.format("%d:%d", dueDate.get(Calendar.HOUR_OF_DAY), dueDate.get(Calendar.MINUTE));
    }

    public String getDueDateString() {
        return String.format("Due Date: %d/%d/%d %s", getYear(), getMonth(), getDate(), getTime());
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
