package com.example.a2340project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MeetingTimesAdapter extends BaseAdapter {
    private List<ClassTime> times;
    private Context context;

    public MeetingTimesAdapter(Context context, List<ClassTime> times) {
        this.context = context;
        this.times = times;
    }
    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public ClassTime getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.meeting_times_list_item, parent, false);
        }

        TextView day = convertView.findViewById(R.id.meeting_time_day);
        TextView time = convertView.findViewById(R.id.meeting_time_duration);

        ClassTime dt = getItem(position);
        day.setText(dt.getDay().toString());
        time.setText(String.format("Starts %s for %d minutes", dt.getTime(), dt.getDuration()));

        return convertView;
    }
}
