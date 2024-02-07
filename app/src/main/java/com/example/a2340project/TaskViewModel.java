package com.example.a2340project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {
    private final MutableLiveData<Task> selectedTask = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNew = new MutableLiveData<>();

    public void setTask(Task task) {
        selectedTask.setValue(task);
    }
    public LiveData<Task> getTask() {
        return selectedTask;
    }

    public void setIsNew(Boolean active) {
        isNew.setValue(active);
    }

    public LiveData<Boolean> isNew() {
        return isNew;
    }
}
