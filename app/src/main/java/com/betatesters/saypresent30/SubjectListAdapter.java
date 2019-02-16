package com.betatesters.saypresent30;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SubjectListAdapter extends ArrayAdapter<SubjectList> {

    private int resourceLayout;
    private Context context;

    // Interface
    private DatabaseMethods databaseMethods;

    private ArrayList<Integer> realIndex;

    public SubjectListAdapter(Context context, int resource, ArrayList<SubjectList> items, DatabaseMethods databaseMethods, ArrayList<Integer> realIndex){
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
        this.databaseMethods = databaseMethods;
        this.realIndex = new ArrayList<>(realIndex);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        SubjectList p = getItem(position);

        if(p != null) {
            TextView section = (TextView)v.findViewById(R.id.list_name);
            TextView subject = (TextView)v.findViewById(R.id.list_subject);
            TextView time = (TextView)v.findViewById(R.id.list_time);
            TextView days = (TextView)v.findViewById(R.id.list_days);

            if(section != null){
                section.setText(p.getSectionName());
            }
            if(subject != null){
                subject.setText(p.getSubjectName());
            }
            if(time != null){
                time.setText(p.getTimeIn() + " - " + p.getTimeOut());
            }
            if(days != null){
                days.setText(p.getWeeks());
            }
        }

        ImageView sideBtn = (ImageView)v.findViewById(R.id.attendance_item_info);

        try{
            sideBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(v.getId()){
                        case R.id.attendance_item_info:
                            PopupMenu popup = new PopupMenu(getContext(), v);
                            popup.getMenuInflater().inflate(R.menu.attendance_popup, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch(item.getItemId()){
                                        case R.id.attendance_edit:
                                            // Do something

                                            Intent i = new Intent(context, EditAttendance.class);
                                            i.putExtra("SubjectIndex", realIndex.get(position));
                                            context.startActivity(i);

                                            break;
                                        case R.id.attendance_delete:
                                            // Do something
                                            new AlertDialog.Builder(getContext())
                                                    .setTitle("Delete Class")
                                                    .setMessage("Do you really want to delete this class?")
                                                    .setIcon(R.drawable.ic_warning_black_24dp)
                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            databaseMethods.deleteSubject(position);
                                                        }})
                                                    .setNegativeButton(android.R.string.no, null).show();
                                            break;
                                        default:
                                            break;
                                    }
                                    return true;
                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return v;
    }
}
