package com.betatesters.saypresent30;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    private ListView listView;
    private ScheduleListAdapter scheduleListAdapter;
    private DatabaseHandler databaseHandler;

    private ArrayList<Integer> subjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Schedule");

        databaseHandler = new DatabaseHandler(this);

        TextView current = (TextView)findViewById(R.id.schedule_date);

        Intent i = getIntent();

        final int month = i.getIntExtra("MONTH", 0);
        final int day = i.getIntExtra("DAY", 0);
        final int week = i.getIntExtra("WEEK", 0);
        final int year = i.getIntExtra("YEAR", 0);

        String textmonth = "January";
        String textweek = "Sunday";

        switch(month){
            case 0:
                textmonth = "January";
                break;
            case 1:
                textmonth = "February";
                break;
            case 2:
                textmonth = "March";
                break;
            case 3:
                textmonth = "April";
                break;
            case 4:
                textmonth = "May";
                break;
            case 5:
                textmonth = "June";
                break;
            case 6:
                textmonth = "July";
                break;
            case 7:
                textmonth = "August";
                break;
            case 8:
                textmonth = "September";
                break;
            case 9:
                textmonth = "October";
                break;
            case 10:
                textmonth = "November";
                break;
            case 11:
                textmonth = "December";
                break;
        }

        switch(week){
            case 1:
                textweek = "Sunday";
                break;
            case 2:
                textweek = "Monday";
                break;
            case 3:
                textweek = "Tuesday";
                break;
            case 4:
                textweek = "Wednesday";
                break;
            case 5:
                textweek = "Thursday";
                break;
            case 6:
                textweek = "Friday";
                break;
            case 7:
                textweek = "Saturday";
                break;
        }

        current.setText(textweek + ", " + textmonth + " " + day);

        subjectID = new ArrayList<>();

        listView = (ListView)findViewById(R.id.schedule_list);
        ArrayList<SubjectList> subjectLists = new ArrayList<>();

        Cursor weekCursor = databaseHandler.getCurrentSchedule(week);


        while(weekCursor.moveToNext()){
            subjectLists.add(new SubjectList(weekCursor.getString(1),weekCursor.getString(0),weekCursor.getString(2),weekCursor.getString(3 ),"NULL"));
            subjectID.add(weekCursor.getInt(5));
        }

        if(!subjectLists.isEmpty()){
            TextView tv = (TextView)findViewById(R.id.empty_sched_placeholder);
            tv.setVisibility(View.INVISIBLE);
        }

        scheduleListAdapter = new ScheduleListAdapter(getApplicationContext(), R.layout.schedule_item, subjectLists);
        listView.setAdapter(scheduleListAdapter);

        weekCursor.close();

        // Log's student's attendance

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ScheduleActivity.this, LogAttendance.class);
                i.putExtra("Subject", subjectID.get(position));
                i.putExtra("MONTH", month);
                i.putExtra("DAY", day);
                i.putExtra("YEAR", year);
                startActivity(i);
            }
        });

    }
}
