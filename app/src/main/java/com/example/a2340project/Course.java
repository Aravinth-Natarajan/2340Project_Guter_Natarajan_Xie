package com.example.a2340project;


import java.util.ArrayList;
import java.util.List;

public class Course {

    private String name;
    private List<ClassTime> classTimes;
    private String location;

    private String section;
    private String instructor;

    public Course(String name) {
        this(name, new ArrayList<>(), "Unknown", "Unknown", "Unknown");
    }

    public Course(String name, List<ClassTime> classTimes, String location, String instructor, String section) {
        this.name = name;
        this.classTimes = classTimes;
        this.location = location;
        this.instructor = instructor;
        this.section = section;
    }

    public String getName() {
        return this.name;
    }

    public List<ClassTime> getClassTimes() {
        return this.classTimes;
    }

    public String getLocation() {
        return this.location;
    }

    public String getInstructor() {
        return this.instructor;
    }


}
