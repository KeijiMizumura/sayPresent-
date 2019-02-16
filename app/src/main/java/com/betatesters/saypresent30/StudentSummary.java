package com.betatesters.saypresent30;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentSummary extends AppCompatActivity {

    private ListView listView;
    private RecordListAdapter recordListAdapter;
    private DatabaseHandler databaseHandler;

    private int subjectID;
    private int studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_summary);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Record List");

        databaseHandler = new DatabaseHandler(this);

        Intent i = getIntent();

        studentID = i.getIntExtra("STUDENT_ID", 0);

        listView = (ListView)findViewById(R.id.record_list);
        ArrayList<RecordList> recordLists = new ArrayList<>();

        Cursor records = databaseHandler.getRecords(studentID);

        while(records.moveToNext()){
            String temp = "";
            switch(records.getInt(6)){
                case 1:
                    temp = "PRESENT";
                    break;
                case 2:
                    temp = "ABSENT";
                    break;
                case 3:
                    temp = "LATE";
                    break;
                case 4:
                    temp = "EXCUSED";
                    break;
            }
            String month = "";
            switch(records.getInt(1)){
                case 1:
                    month = "January";
                    break;
                case 2:
                    month = "February";
                    break;
                case 3:
                    month = "March";
                    break;
                case 4:
                    month = "April";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "June";
                    break;
                case 7:
                    month = "July";
                    break;
                case 8:
                    month = "August";
                    break;
                case 9:
                    month = "September";
                    break;
                case 10:
                    month = "October";
                    break;
                case 11:
                    month = "November";
                    break;
                case 12:
                    month = "December";
                    break;
            }

            recordLists.add(new RecordList(month + " " + records.getInt(2) + ", " + records.getInt(3), temp));
        }



        if(!recordLists.isEmpty()){
            TextView tv = (TextView)findViewById(R.id.empty_record_placeholder);
            tv.setVisibility(View.INVISIBLE);
        }


        recordListAdapter = new RecordListAdapter(this, R.layout.summary_log_item, recordLists);
        listView.setAdapter(recordListAdapter);

    }
}
