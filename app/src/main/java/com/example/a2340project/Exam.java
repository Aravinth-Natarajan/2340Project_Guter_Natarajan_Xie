package com.example.a2340project;
import java.util.Date;

public class Exam {
    private Date dueDate;
    private String detail;
    private String location;

    public Exam(Date dueDate, String location, String detail) {
        this.dueDate = dueDate;
        this.detail = detail;
        this.location = location;
    }

    public Exam(Date dueDate, String location) {
        this(dueDate, location, "");
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getLocation() {
        return location;
    }

    public String getDetail() {
        return detail;
    }
}
