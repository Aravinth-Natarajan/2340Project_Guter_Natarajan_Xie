package com.example.a2340project;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {

    private List<Task> taskList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    public static class ToDoListViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDueDate;
        private final TextView textViewDescription;
        private final TextView textViewCourse;
        private final CheckBox checkBox;
        private final ConstraintLayout con;
        public int getRandomColor(){
            Random rnd = new Random();
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        }

        public ToDoListViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            con = view.findViewById(R.id.todo_list_card_view);
            textViewTitle = view.findViewById(R.id.todo_list_item_title);
            textViewDueDate = view.findViewById(R.id.todo_list_item_dueDate);
            textViewDescription = view.findViewById(R.id.todo_list_item_description);
            textViewCourse = view.findViewById(R.id.todo_list_item_course);
            checkBox = view.findViewById(R.id.checkBox);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }
        public TextView getTextViewDueDate() {
            return textViewDueDate;
        }
        public TextView getTextViewDescription() {
            return textViewDescription;
        }

        public TextView getTextViewCourse() { return textViewCourse;}

        public ConstraintLayout getCon() {
            return con;
        }
        public CheckBox getCheck() {return checkBox; }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param taskList ArrayList containing the data to populate views to be used
     * by RecyclerView
     */
    public ToDoListAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.todo_list_item, viewGroup, false);

        return new ToDoListViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ToDoListViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextViewTitle().setText(taskList.get(position).getTitle());
        viewHolder.getTextViewDueDate().setText(taskList.get(position).getDueDateString());
        viewHolder.getTextViewDescription().setText(taskList.get(position).getDescription());
        if (taskList.get(position).getCourse() == null)
            viewHolder.getTextViewCourse().setText("No Course");
        else
            viewHolder.getTextViewCourse().setText(taskList.get(position).getCourse().toString());
        viewHolder.getCon().setBackgroundColor(viewHolder.getRandomColor());
        viewHolder.getCheck().setChecked(taskList.get(position).getChecked());
        viewHolder.getCheck().setOnClickListener((v) -> {
            taskList.get(position).setCheck();
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskList.size();
    }
}


