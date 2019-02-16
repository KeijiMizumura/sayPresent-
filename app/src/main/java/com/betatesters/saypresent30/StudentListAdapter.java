package com.betatesters.saypresent30;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.betatesters.saypresent30.R;
import com.betatesters.saypresent30.StudentsList;

import java.util.ArrayList;

public class StudentListAdapter extends ArrayAdapter<StudentsList> {

    private int resourceLayout;
    private Context context;
    private DatabaseMethods databaseMethods;

//    values

    private int subjectIndex;
    private ArrayList<Integer> studentIndex;

    public StudentListAdapter(Context context, int resource, ArrayList<StudentsList> items, DatabaseMethods databaseMethods, int subjectIndex, ArrayList<Integer> studentIndex){
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
        this.databaseMethods = databaseMethods;
        this.subjectIndex = subjectIndex;
        this.studentIndex = new ArrayList<>(studentIndex);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        StudentsList p = getItem(position);

        if(p != null) {
            TextView fullName = (TextView)v.findViewById(R.id.list_student_name);


            if(fullName != null){
                fullName.setText(p.getLastName() + ", " + p.getFirstName());
            }

        }


        ImageView sideBtn = (ImageView)v.findViewById(R.id.students_item_info);

        try{
            sideBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(v.getId()){
                        case R.id.students_item_info:
                            PopupMenu popup = new PopupMenu(getContext(), v);
                            popup.getMenuInflater().inflate(R.menu.students_popup, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch(item.getItemId()){
                                        case R.id.student_edit:
                                            // Do something

                                            Intent i = new Intent(context, EditStudent.class);
                                            i.putExtra("StudentIndex", studentIndex.get(position));
                                            i.putExtra("StudentSubjectID", subjectIndex);
                                            context.startActivity(i);

                                            break;
                                        case R.id.student_delete:
                                            // Do something
                                            new AlertDialog.Builder(getContext())
                                                    .setTitle("Delete Student")
                                                    .setMessage("Do you really want to delete this student?")
                                                    .setIcon(R.drawable.ic_warning_black_24dp)
                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            databaseMethods.deleteStudent(position);
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
