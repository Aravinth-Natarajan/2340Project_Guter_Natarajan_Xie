package com.example.a2340project;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {

    private ToDoList taskList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    public static class ToDoListViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView2;
        private final TextView textView3;

        private final ConstraintLayout con;
        public int getRandomColor(){
            Random rnd = new Random();
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        }

        public ToDoListViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            con = view.findViewById(R.id.todo_list_card_view);
            textView = view.findViewById(R.id.todo_list_item_title);
            textView2 = view.findViewById(R.id.todo_list_item_dueDate);
            textView3 = view.findViewById(R.id.todo_list_item_description);
        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getTextView2() {
            return textView2;
        }
        public TextView getTextView3() {
            return textView3;
        }
        public ConstraintLayout getCon() {
            return con;
        }

    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param taskList ArrayList containing the data to populate views to be used
     * by RecyclerView
     */
    public ToDoListAdapter(ToDoList taskList) {
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
        viewHolder.getTextView().setText(taskList.returnList().get(position).getTitle());
//        viewHolder.getTextView2().setText(taskList.returnList().get(position).getTitle());
        viewHolder.getTextView3().setText(taskList.returnList().get(position).getDescription());
        viewHolder.getCon().setBackgroundColor(viewHolder.getRandomColor());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskList.returnList().size();
    }
}


