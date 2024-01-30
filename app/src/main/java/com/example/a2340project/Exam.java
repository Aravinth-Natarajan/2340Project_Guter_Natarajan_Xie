package com.example.a2340project;
import java.util.Date;

public class Exam extends Task{
    private String location;
    private Course course;

    public Exam(Date dueDate, String location, String description, String title, Course course) {
        super(title, dueDate, description);
        this.location = location;
        this.course = course;
    }

    public Exam(Date dueDate, String location, Course course) {
        this(dueDate, location, "", "", course);
    }


    public String getLocation() {
        return location;
    }

    public Course getCourse() {
        return course;
    }

}
