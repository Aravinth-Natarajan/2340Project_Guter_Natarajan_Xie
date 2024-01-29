package com.example.a2340project;

import java.util.Date;

public class Assignment {
    private Date dueDate;
    private String detail;
    private String title;

    public Assignment(Date dueDate, String detail, String title) {
        this.dueDate = dueDate;
        this.detail = detail;
        this.title = title;
    }

    public Assignment(Date dueDate, String title) {
        this(dueDate, title, "");
    }

    public Assignment(Date dueDate) {
        this(dueDate, "");
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDetail() {
        return detail;
    }

    public String getTitle() {
        return title;
    }
}
