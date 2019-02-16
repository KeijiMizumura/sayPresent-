package com.betatesters.saypresent30;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditStudent extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Student");

        databaseHandler = new DatabaseHandler(this);

        Intent i = getIntent();

        final int studID = i.getIntExtra("StudentIndex", 0);
        final int subID = i.getIntExtra("StudentSubjectID", 0);


        final EditText fname = (EditText)findViewById(R.id.student_fname);
        final EditText lname = (EditText)findViewById(R.id.student_lname);

        Cursor cursor = databaseHandler.getStudents(studID, subID);
        while(cursor.moveToNext()){
            fname.setText(cursor.getString(1));
            lname.setText(cursor.getString(2));
        }

        Button btn = (Button)findViewById(R.id.edit_student_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean updateStudent = databaseHandler.updateStudent(studID + "", fname.getText().toString(), lname.getText().toString(), subID + "");
                if(updateStudent){
                    Intent i = new Intent(EditStudent.this, MainActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }


}
