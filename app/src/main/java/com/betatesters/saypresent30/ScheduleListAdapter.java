package com.betatesters.saypresent30;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleListAdapter extends ArrayAdapter<SubjectList> {

    private int resourceLayout;
    private Context context;

    public ScheduleListAdapter(Context context, int resource, ArrayList<SubjectList> items){
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        SubjectList p = getItem(position);

        if(p != null){
            TextView section = (TextView)v.findViewById(R.id.list_name);
            TextView subject = (TextView)v.findViewById(R.id.list_subject);
            TextView time = (TextView)v.findViewById(R.id.list_time);


            if(section != null){
                section.setText(p.getSectionName());
            }
            if(subject != null){
                subject.setText(p.getSubjectName());
            }
            if(time != null){
                time.setText(p.getTimeIn() + " - " + p.getTimeOut());
            }

        }

        return v;

    }
}
