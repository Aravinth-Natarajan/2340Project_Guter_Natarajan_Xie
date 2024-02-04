package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2340project.databinding.TodoFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Comparator;

public class TodoFragment extends Fragment {

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

        Course test1Course = new Course("B3511");
        Course test2Course = new Course("Z2200");
        Course test3Course = new Course("H4641");

        toDoList = new ToDoList();
        toDoList.addTask(new Task("Done", LocalDateTime.now(), "Jaosdija Hi", test3Course));
        toDoList.addTask(new Task("Done2", LocalDateTime.now(), "Venky Hi", test1Course));
        toDoList.addTask(new Task("Currently working", LocalDateTime.now().minusDays(2), "Jaosdija Hi", test2Course));
        toDoList.addTask(new Task("AAAAAYyyyy", LocalDateTime.now(), "Jaosdija Hi"));
        toDoList.addTask(new Task("Get This project Done Earlier", LocalDateTime.now().minusDays(6), "Jaosdija Hi"));

        ToDoListAdapter toDoListAd = new ToDoListAdapter(toDoList);
        binding.todoListView.setAdapter(toDoListAd);
        binding.todoListView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/

        binding.sortByButton.setOnClickListener((v) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.setOnMenuItemClickListener(this::onTodoListSortOptionClicked);
            popupMenu.getMenuInflater().inflate(R.menu.todo_sort_menu, popupMenu.getMenu());
            popupMenu.show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        toDoList.returnList().sort(new TaskClassComparator());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean onTodoListSortOptionClicked(MenuItem option) {
        int id = option.getItemId();

        // TODO: implement sorting with/without completed tasks
        if (id == R.id.sort_by_classes) {
            Snackbar.make(binding.getRoot(), "Sort by classes button", 3000).show();
            return true;
        }
        else if (id == R.id.sort_by_due_date) {
            Snackbar.make(binding.getRoot(), "Sort by due date button", 3000).show();
            return true;
        }
        else {
            return false;
        }
    }
}