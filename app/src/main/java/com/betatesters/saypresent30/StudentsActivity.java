package com.betatesters.saypresent30;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity implements DatabaseMethods{

    private ListView listView;
    private StudentListAdapter studentListAdapter;
    private DatabaseHandler databaseHandler;
    private ArrayList<Integer> StudentID;
    private int SubjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Students List");

        databaseHandler = new DatabaseHandler(this);

        Intent mIntent = getIntent();
        SubjectID = mIntent.getIntExtra("SUBJECT_ID", 0);

        TextView placeholder = (TextView)findViewById(R.id.empty_list_placeholder);

        placeholder.setText("NO STUDENTS IN THIS SECTION");

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.add_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentsActivity.this, AddStudent.class);
                i.putExtra("SUBJ_ID", SubjectID);
                finish();
                startActivity(i);
            }
        });

        StudentID = new ArrayList<>();

        listView = (ListView)findViewById(R.id.list_of_students);
        ArrayList<StudentsList> studentsLists = new ArrayList<>();

        Cursor students = databaseHandler.getStudents(SubjectID);

        while(students.moveToNext()){
            studentsLists.add(new StudentsList(students.getString(1),students.getString(2)));
            StudentID.add(students.getInt(0));
        }


        if(!studentsLists.isEmpty()){
            TextView tv = (TextView)findViewById(R.id.empty_list_placeholder);
            tv.setVisibility(View.INVISIBLE);
        }


        studentListAdapter = new StudentListAdapter(this, R.layout.student_item, studentsLists, this, SubjectID, StudentID);
        listView.setAdapter(studentListAdapter);

        // ListView click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getContext(), "Subject ID: " + subjectID.get(position), Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(), "Student Number " + StudentID.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(StudentsActivity.this, StudentSummary.class);
                i.putExtra("STUDENT_ID", StudentID.get(position));
                startActivity(i);
            }
        });

        students.close();

    }


    public void deleteSubject(int id){
        // I wont use this method
    }

    public void deleteStudent(int studID){
        databaseHandler.deleteStudent(SubjectID + "", StudentID.get(studID).toString());
        reloadActivity();
    }

    public void reloadActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
