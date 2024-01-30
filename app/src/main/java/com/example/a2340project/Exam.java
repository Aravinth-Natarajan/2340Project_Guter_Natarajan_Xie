package com.example.a2340project;
import java.util.Date;

public class Exam {
    private Date dueDate;
    private String detail;
    private String location;

    private String name;

    public Exam(Date dueDate, String location, String detail, String name) {
        this.dueDate = dueDate;
        this.detail = detail;
        this.location = location;
        this.name = name;
    }

    public Exam(Date dueDate, String location) {
        this(dueDate, location, "", "");
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

    public String getName() {
        return name;
    }

}
