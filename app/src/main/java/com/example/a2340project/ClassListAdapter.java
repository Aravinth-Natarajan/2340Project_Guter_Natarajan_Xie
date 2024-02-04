package com.example.a2340project;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ViewHolder> {

    private List<Course> courseList;
    private FragmentManager fragManager;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View root;
        private final TextView textView;
        private final TextView textView2;
        private final TextView textView3;

        private final ConstraintLayout con;
        public int getRandomColor(){
            Random rnd = new Random();
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        }

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            root = view;
            con = view.findViewById(R.id.class_list_item_card_color_layout);
            textView = view.findViewById(R.id.class_list_item_course_name);
            textView2 = view.findViewById(R.id.class_list_item_class_section);
            textView3 = view.findViewById(R.id.class_list_item_location);
        }

        public View getRoot() {
            return root;
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
     * @param courseList ArrayList containing the data to populate views to be used
     * by RecyclerView
     */
    public ClassListAdapter(List<Course> courseList, FragmentManager fragManager) {
        this.courseList = courseList;
        this.fragManager = fragManager;
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
        Course course = courseList.get(position);
        viewHolder.getTextView().setText(course.getName());
        viewHolder.getTextView2().setText(course.getSection());
        viewHolder.getTextView3().setText(course.getLocation());
        viewHolder.getCon().setBackgroundColor(viewHolder.getRandomColor());

        viewHolder.getRoot().setOnClickListener((v) -> {
            fragManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ClassDetailsFragment(course))
                    .commit();
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courseList.size();
    }
}


