package com.example.a2340project;
import java.util.Date;
import java.util.UUID;
public class Task {
    String title;
    Date dueDate;
    String description;
    String uniqueID;

    private boolean check = false;

    public Task(String title, Date dueDate, String description) {
        this.description = description;
        this.dueDate = dueDate;
        this.title = title;
        this.uniqueID = UUID.randomUUID().toString();
    }
    public Task(String title, String description) {
        this(title, null, description);
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

    public Date getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
