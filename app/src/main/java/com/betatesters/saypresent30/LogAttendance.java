package com.betatesters.saypresent30;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LogAttendance extends AppCompatActivity {

    private ListView listView;
    private LogListAdapter logListAdapter;
    private DatabaseHandler databaseHandler;

    private ArrayList<Integer> studentID;

    private Button button;

    private Boolean isMissing;

    private Boolean alreadyLogged;

    private int month;
    private int day;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_attendance);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Log Attendance");

        button = (Button)findViewById(R.id.log_submit_btn);

        studentID = new ArrayList<>();

        Intent i = getIntent();
        final int subject_id = i.getIntExtra("Subject", 0);
        month = i.getIntExtra("MONTH", 0) + 1;
        day = i.getIntExtra("DAY", 0);
        year = i.getIntExtra("YEAR", 0);

        databaseHandler = new DatabaseHandler(this);

        listView = (ListView)findViewById(R.id.student_log_list);
        ArrayList<LogList> logLists = new ArrayList<>();

        final Cursor students = databaseHandler.getStudents(subject_id);

        while(students.moveToNext()){
            logLists.add(new LogList(students.getString(1), students.getString(2)));
            studentID.add(students.getInt(0));
        }

        if(!logLists.isEmpty()){
            TextView tv = (TextView)findViewById(R.id.empty_log_placeholder);
            tv.setVisibility(View.INVISIBLE);
        }


        logListAdapter = new LogListAdapter(this, R.layout.student_log_item, logLists);
        try {
            listView.setAdapter(logListAdapter);
        }
        catch(Exception e){
            button.setVisibility(View.INVISIBLE);
        }

        isMissing = false;
        alreadyLogged = false;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.log_submit_btn){
                    ArrayList<LogList> items = logListAdapter.getItems();
                    ArrayList<Integer> presentID = new ArrayList<>();

                    for(LogList log : items){
                        if(log.selectedId != -1){
                            Log.i("TAG", "Title " + log.getLastName() + " Selected " + (log.selectedId == R.id.radio_absent ? "A" : "P"));

                            switch(log.selectedId){
                                case R.id.radio_present:
                                    presentID.add(1);
                                    break;
                                case R.id.radio_absent:
                                    presentID.add(2);
                                    break;
                                case R.id.radio_late:
                                    presentID.add(3);
                                    break;
                                case R.id.radio_leave:
                                    presentID.add(4);
                                    break;
                            }

                        }else{
                            isMissing = true;
                        }
                    }

                    if(isMissing){
                        Toast.makeText(getApplicationContext(), "Incomplete Details", Toast.LENGTH_SHORT).show();
                        isMissing = false;
                    }
                    else{
                        for(int rows = 0; rows < studentID.size(); rows++){
                            Log.i("TAG", "STUDENT NO. " + studentID.get(rows) + ", PRESENT ID. " + presentID.get(rows) + " | CURRENT DATE: " + month + "/" + day + "/" + year);
                            Cursor prevRecords = databaseHandler.getRecords(month, day, year, subject_id, studentID.get(rows));
                            if(prevRecords.getCount() == 0){
                                databaseHandler.addRecords(month, day, year, subject_id, studentID.get(rows), presentID.get(rows));
                                Toast.makeText(getApplicationContext(), "Successful logged attendance", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                alreadyLogged = true;
                            }
                            prevRecords.close();
                        }
                    }

                    if(alreadyLogged){
                        Snackbar.make(v, "You already logged this subject.", Snackbar.LENGTH_SHORT).setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                                .setActionTextColor(getResources().getColor(R.color.white))
                                .show();
                    }

                }
            }
        });

    }
}
