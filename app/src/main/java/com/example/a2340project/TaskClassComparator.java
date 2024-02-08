package com.example.a2340project;

import java.util.Comparator;

public class TaskClassComparator implements Comparator<Task>  {
    @Override
    public int compare(Task o1, Task o2) {
        int classComp;

        if (o1.getCourse() == null && o2.getCourse() != null) {
            classComp = 1;
        } else if (o1.getCourse() != null && o2.getCourse() == null) {
            classComp = -1;
        } else if (o1.getCourse() == null && o2.getCourse() == null) {
            classComp = 0;
        } else {
            classComp = o1.getCourse().compareTo(o2.getCourse());
        }

        return classComp;
    }
}
