package com.example.a2340project;

import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void Student_IsCreated() {
        Student testStudent = new Student("Venky");
        assertEquals(testStudent.name, "Venky");
    }

    @Test
    public void ClassTime_IsCreated() {
        ClassTime testClassTime = new ClassTime();
        assertEquals(testClassTime.getTime(), "12:30");
        assertEquals(testClassTime.getDay(), DayOfWeek.MONDAY);
        assertEquals(testClassTime.getDuration(), 50);
        assertEquals(testClassTime.toString(), "MONDAY 12:30 50 minutes");
    }
    @Test
    public void Course_IsCreated() {
        Course testCourse = new Course("CS 2340");
        assertEquals(testCourse.getName(), "CS 2340");
    }
    @Test
    public void Task_IsCreated() {
        Date date = new Date();
        Task testTask = new Task("Workout", date, "Workout 5 times");
        assertEquals(testTask.title, "Workout");
        assertEquals(testTask.dueDate, date);
        assertEquals(testTask.description, "Workout 5 times");
    }
    @Test
    public void ToDoList_IsCreated() {
        Date date = new Date();
        Task testTask = new Task("Workout", date, "Workout 5 times");
        ToDoList testList = new ToDoList();
        testList.addTask(testTask);
        System.out.println(testList.returnList());
        testList.removeTask(testTask);
        System.out.println(testList.returnList());
    }
}