package com.example.a2340project;

import org.junit.Test;
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
    public void Classes_IsCreated() {

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