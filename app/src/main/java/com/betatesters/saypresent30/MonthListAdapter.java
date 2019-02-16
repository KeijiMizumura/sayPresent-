package com.betatesters.saypresent30;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MonthListAdapter extends ArrayAdapter<Months> {

    private int resourceLayout;
    private Context context;

    public MonthListAdapter(Context context, int resource, ArrayList<Months> items){
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

        Months p = getItem(position);

        if(p != null){
            TextView tv = (TextView)v.findViewById(R.id.month_list_text);

            if(tv != null){
                tv.setText(p.getMonth());
            }
        }

        return  v;

    }
}
