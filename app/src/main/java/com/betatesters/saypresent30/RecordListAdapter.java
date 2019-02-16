package com.betatesters.saypresent30;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecordListAdapter extends ArrayAdapter<RecordList> {

    private int resourceLayout;
    private Context context;

    public RecordListAdapter(Context context, int resource, ArrayList<RecordList> items){
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

        RecordList p = getItem(position);

        if(p != null){
            TextView date = (TextView)v.findViewById(R.id.list_summary_date);
            TextView state = (TextView)v.findViewById(R.id.student_status);

            if(date != null){
                date.setText(p.getDate());
            }

            if(state != null){
                state.setText(p.getStatus());
                if(state.getText() == "PRESENT"){
                    state.setTextColor(ContextCompat.getColor(context, R.color.presentColor));
                }
                if(state.getText() == "ABSENT"){
                    state.setTextColor(ContextCompat.getColor(context, R.color.absentColor));
                }
                if(state.getText() == "LATE"){
                    state.setTextColor(ContextCompat.getColor(context, R.color.lateColor));
                }
                if(state.getText() == "LEAVE"){
                    state.setTextColor(ContextCompat.getColor(context, R.color.leaveColor));
                }
            }
        }

        return v;

    }
}
