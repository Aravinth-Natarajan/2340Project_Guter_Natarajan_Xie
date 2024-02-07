package com.example.a2340project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2340project.databinding.TodoFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class TodoFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

private TodoFragmentBinding binding;

private ArrayList<Task> tasks;
private ArrayList<Task> incompleteTasks;
private ToDoList toDoList;

    public TodoFragment(ToDoList toDoList) {
        this.toDoList = toDoList;
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = TodoFragmentBinding.inflate(inflater, container, false);

        // TODO: get rid of this later
        tasks = new ArrayList<>();
        incompleteTasks = new ArrayList<>();

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.TASK_LIST);

        ToDoListAdapter toDoListAd = new ToDoListAdapter(toDoList.returnList());
        binding.todoListView.setAdapter(toDoListAd);
        binding.todoListView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.sortByButton.setOnClickListener((v) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.getMenuInflater().inflate(R.menu.todo_sort_menu, popupMenu.getMenu());
            popupMenu.show();
//            popupMenu.setOnMenuItemClickListener(this::onTodoListSortOptionClicked);
        });


        binding.showCompletedButton.setOnClickListener((v) -> {
            onClickRender(false, false);
        });

        binding.showIncompletedButton.setOnClickListener((v) -> {
            onClickRender(false, false);
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.sort_by_classes) {
            onClickRender(true, false);
        }
        else
            onClickRender(false, true);
        return true;
    }


    private void onClickRender(boolean sortCourse, boolean sortDate) {
        boolean incomplete = binding.showIncompletedButton.isChecked();
        boolean complete = binding.showCompletedButton.isChecked();
        ToDoListAdapter toDoListAd = new ToDoListAdapter(toDoList.returnTask(sortCourse, sortDate, complete, incomplete));
        binding.todoListView.setAdapter(toDoListAd);
        binding.todoListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
//        toDoList.returnList().sort(new TaskClassComparator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}