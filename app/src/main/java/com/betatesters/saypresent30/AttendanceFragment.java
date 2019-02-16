package com.betatesters.saypresent30;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFragment extends Fragment implements DatabaseMethods{

    private ListView listView;
    private SubjectListAdapter subjectListAdapter;
    private DatabaseHandler databaseHandler;
    private ArrayList<Integer> subjectID;

    public void deleteSubject(int id){
        // do stuff to delete
        databaseHandler.deleteSubject(subjectID.get(id).toString());
        refreshFragment();
    }

    public void deleteStudent(int studID){}


    public void refreshFragment(){
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHandler = new DatabaseHandler(getContext());

        FloatingActionButton fab = view.findViewById(R.id.add_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddAttendance.class);
                startActivity(i);
            }
        });

        subjectID = new ArrayList<>();

        listView = view.findViewById(R.id.list_of_attendance);
        ArrayList<SubjectList> subjectLists = new ArrayList<>();

        Cursor subjects = databaseHandler.getSubjects();


        String weekValues = "";
        while(subjects.moveToNext()){
            Cursor daysOfTheWeek = databaseHandler.getDaysOfTheWeek(subjects.getInt(0));
            while(daysOfTheWeek.moveToNext()){
                switch(daysOfTheWeek.getInt(1)){
                    case 1:
                        weekValues += " S ";
                        break;
                    case 2:
                        weekValues += " M ";
                        break;
                    case 3:
                        weekValues += " T ";
                        break;
                    case 4:
                        weekValues += " W ";
                        break;
                    case 5:
                        weekValues += " T ";
                        break;
                    case 6:
                        weekValues += " F ";
                        break;
                    case 7:
                        weekValues += " S ";
                        break;
                }
            }
            subjectID.add(subjects.getInt(0));
            subjectLists.add(new SubjectList(subjects.getString(2), subjects.getString(1),subjects.getString(3),subjects.getString(4 ), weekValues));
            weekValues = "";
         }
         subjects.close();

        // if list is not empty

        if(!subjectLists.isEmpty()){
            TextView tv = view.findViewById(R.id.empty_list_placeholder);
            tv.setVisibility(View.INVISIBLE);
        }


        subjectListAdapter = new SubjectListAdapter(getContext(), R.layout.row_item, subjectLists, this, subjectID);
        listView.setAdapter(subjectListAdapter);

        // ListView click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getContext(), "Subject ID: " + subjectID.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), StudentsActivity.class);
                i.putExtra("SUBJECT_ID", subjectID.get(position));
                startActivity(i);
            }
        });
    }

    public void toastMessage(String message){

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);

    }

}
