package com.example.a2340project;

import java.util.Comparator;

public class TaskDateComparator implements Comparator<Task>  {
    @Override
    public int compare(Task o1, Task o2) {
        int dateComp = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateComp = o1.getDueDate().compareTo(o2.getDueDate());
        }

        return dateComp;
    }
}
