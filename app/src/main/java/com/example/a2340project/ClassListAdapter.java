package com.example.a2340project;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

    public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ViewHolder> {

    private ArrayList<Course> courseList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView2;
        private final TextView textView3;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.class_list_item_course_name);
            textView2 = view.findViewById(R.id.class_list_item_class_section);
            textView3 = view.findViewById(R.id.class_list_item_location);
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

    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param courseList ArrayList containing the data to populate views to be used
     * by RecyclerView
     */
    public ClassListAdapter(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.class_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(courseList.get(position).getName());
        viewHolder.getTextView2().setText(courseList.get(position).getSection());
        viewHolder.getTextView3().setText(courseList.get(position).getLocation());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courseList.size();
    }
}


