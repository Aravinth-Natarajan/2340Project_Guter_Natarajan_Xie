package com.example.a2340project;

import java.util.Date;

public class Assignment extends Task{
    private Course course;


    public Assignment(Date dueDate, String description, String title, Course course) {
        super(title, dueDate, description);
        this.course = course;
    }

    public Assignment(Date dueDate, Course course) {
        this(dueDate, "", "", course);
    }

    public Course getCourse() {
        return course;
    }


}
