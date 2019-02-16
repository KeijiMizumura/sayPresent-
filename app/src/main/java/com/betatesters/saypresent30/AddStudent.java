package com.betatesters.saypresent30;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddStudent extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        databaseHandler = new DatabaseHandler(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Student");

        Intent i = getIntent();
        int position = i.getIntExtra("SUBJ_ID", 0);

        TextView section = (TextView)findViewById(R.id.add_student_title);
        TextView subject =(TextView)findViewById(R.id.add_student_title_subject);

        Cursor data = databaseHandler.getSubjects(position);

        while(data.moveToNext()){
            section.setText(data.getString(2));
            subject.setText(data.getString(1));
        }


        // submit's the data
        submit(position);

    }

    public void submit(int id){
        final TextView fname = (TextView)findViewById(R.id.student_fname);
        final TextView lname = (TextView)findViewById(R.id.student_lname);
        final int Subject_ID = id;

        Button addStudent = (Button)findViewById(R.id.add_student_btn);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String First = fname.getText().toString();
                String Last = lname.getText().toString();

                if(!(First.equals("") || Last.equals(""))){
                    addData(First, Last, Subject_ID);
                    fname.setText("");
                    lname.setText("");
                }
                else{
                    toastMessage("Fill up the fields");
                }

            }
        });

        Button goBack = (Button)findViewById(R.id.return_home);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddStudent.this, StudentsActivity.class);
                i.putExtra("SUBJECT_ID", Subject_ID);
                finish();
                startActivity(i);
            }
        });
    }

    public void addData(String fname, String lname, int id){
        boolean insertData = databaseHandler.addStudent(fname, lname, id);
        if(insertData){
            toastMessage("Data Successfully Inserted");
        }
        else{
            toastMessage("Something went wrong");
        }
    }

    public void toastMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
