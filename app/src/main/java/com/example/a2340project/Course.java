package com.example.a2340project;


import java.util.ArrayList;
import java.util.List;

public class Course implements Comparable<Course> {

    private String name;
    private List<ClassTime> classTimes;
    private String location;

    private String section;
    private String instructor;

    public Course(String name) {
        this(name, new ArrayList<>(), "Unknown", "Unknown", "Unknown");
    }
    public Course(String name, String location, String section) {
        this(name, new ArrayList<>(), location, "Unknown", section);
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
    public String getSection() {
        return this.section;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Course o) {
        return this.name.compareTo(o.name);
    }
}
