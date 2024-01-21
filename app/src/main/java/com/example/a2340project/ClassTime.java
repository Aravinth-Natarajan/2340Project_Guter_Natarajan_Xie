package com.example.a2340project;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ClassTime {
    private DayOfWeek day;
    private String time;
    private int duration;// store class duration as minute

    public ClassTime(DayOfWeek day, String time, int duration) {
        this.day = day;
        this.time = time;
        this.duration = duration;
    }

    public ClassTime(DayOfWeek day, String time) {
        this(day, time, 50);
    }

    public ClassTime(DayOfWeek day) {
        this(day, "12:30");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ClassTime() {
        this(DayOfWeek.MONDAY);
    }

    public DayOfWeek getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return day + " " + time + " " + duration + " minutes";
    }
}
