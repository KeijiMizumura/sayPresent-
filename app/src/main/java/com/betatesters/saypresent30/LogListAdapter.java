package com.betatesters.saypresent30;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LogListAdapter extends ArrayAdapter<LogList> {

    private int resourceLayout;
    private Context context;
    private ArrayList<LogList> data;


    private boolean[] presentState = null;
    private boolean[] absentState = null;
    private boolean[] lateState = null;
    private boolean[] leaveState = null;

    public LogListAdapter(Context context, int resource, ArrayList<LogList> items){
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
        this.data = new ArrayList<>(items);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MatrixHolder holder = null;


        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);

            holder = new MatrixHolder();
            holder.studentName = (TextView)v.findViewById(R.id.list_student_name);

            holder.group = (RadioGroup)v.findViewById(R.id.radio_student);

            holder.present = (RadioButton)v.findViewById(R.id.radio_present);
            holder.absent = (RadioButton)v.findViewById(R.id.radio_absent);
            holder.late = (RadioButton)v.findViewById(R.id.radio_late);
            holder.leave = (RadioButton)v.findViewById(R.id.radio_leave);

            v.setTag(holder);
        }else{
            holder = (MatrixHolder) v.getTag();
        }


        holder.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int pos = (int) group.getTag();
                data.get(pos).selectedId = group.getCheckedRadioButtonId();
            }
        });


        LogList logList = data.get(position);
        holder.studentName.setText(logList.getLastName() + ", " + logList.getFirstName());
        holder.group.setTag(position);

        if(logList.selectedId != -1){
            holder.group.check(logList.selectedId);
        }


        return v;

    }

    public ArrayList<LogList> getItems(){
        return data;
    }

    static class MatrixHolder {
        TextView studentName;
        RadioGroup group;
        RadioButton present;
        RadioButton absent;
        RadioButton late;
        RadioButton leave;
        int position;
    }

    @Override
    public int getViewTypeCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
