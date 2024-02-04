package com.example.a2340project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CourseViewModel extends ViewModel {
    private final MutableLiveData<Course> selectedCourse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNew = new MutableLiveData<>();

    public void setCourse(Course course) {
        selectedCourse.setValue(course);
    }
    public LiveData<Course> getCourse() {
        return selectedCourse;
    }

    public void setIsNew(Boolean active) {
        isNew.setValue(active);
    }

    public LiveData<Boolean> isNew() {
        return isNew;
    }
}
