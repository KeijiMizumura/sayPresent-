package com.betatesters.saypresent30;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddAttendance extends AppCompatActivity {

    // Database Helper

    private DatabaseHandler databaseHandler;

    private int hourNotif = 0;
    private int minuteNotif = 0;


    // Days of the week variables

    private boolean sundayIsChecked = false;
    private boolean mondayIsChecked = false;
    private boolean tuesdayIsChecked = false;
    private boolean wednesdayIsChecked = false;
    private boolean thursdayIsChecked = false;
    private boolean fridayIsChecked = false;
    private boolean saturdayIsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Section");


//        View parentLayout = findViewById(android.R.id.content);
//        Snackbar.make(parentLayout, "This is main activity", Snackbar.LENGTH_LONG)
//                .setAction("CLOSE", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(), "Snackbar Closed", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
//                .show();
        // methods

        // Instantiate Database Handler

        databaseHandler = new DatabaseHandler(this);

        times();
        DaysOfTheWeek();
        submit();
    }

    // If user clicks the submit button

    public void submit(){
        final EditText section = (EditText)findViewById(R.id.section_field);
        final EditText subject = (EditText)findViewById(R.id.subject_field);
        final EditText fromTime = (EditText)findViewById(R.id.from_time);
        final EditText untilTime = (EditText)findViewById(R.id.until_time);

        Button btn = (Button)findViewById(R.id.submit_fields);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sectionName = section.getText().toString();
                String subjectName = subject.getText().toString();
                String from = fromTime.getText().toString();
                String until = untilTime.getText().toString();

                if(!(sectionName.equals("") || subjectName.equals("") || from.equals("") || until.equals(""))){
                    // If the fields are complete
                    if(sundayIsChecked || mondayIsChecked || tuesdayIsChecked || wednesdayIsChecked || thursdayIsChecked || fridayIsChecked || saturdayIsChecked){
                        // Do something if everything is good
                        ArrayList<Integer> weeks = new ArrayList<>();
                        if(sundayIsChecked){
                            weeks.add(1);
                        }
                        if(mondayIsChecked){
                            weeks.add(2);
                        }
                        if(tuesdayIsChecked){
                            weeks.add(3);
                        }
                        if(wednesdayIsChecked){
                            weeks.add(4);
                        }
                        if(thursdayIsChecked){
                            weeks.add(5);
                        }
                        if(fridayIsChecked){
                            weeks.add(6);
                        }
                        if(saturdayIsChecked){
                            weeks.add(7);
                        }

                        // ADDING DATA
                        addData(subjectName, sectionName, from, until, weeks);

                        /*
                        *
                        * TODO: This is where you initialize the notification in the device
                        *
                        *
                        * TODO: Not yet implemented, but working on it
                        */


                        Intent i = new Intent(AddAttendance.this, MainActivity.class);
                        finish();
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Fields Incomplete", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    // If the fields are incomplete
                    Toast.makeText(getApplicationContext(), "Fields Incomplete", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

//    Entering data to the database

    public void addData(String name, String section, String timeIn, String timeOut, ArrayList<Integer> week){
        boolean insertData = databaseHandler.addSubject(name, section, timeIn, timeOut, week);
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


    public void times(){
        final EditText fromTime = (EditText)findViewById(R.id.from_time);
        fromTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    hourNotif = hour;
                    minuteNotif = minute;
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AddAttendance.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String MM = "AM";
                            if(selectedHour < 12){
                                MM = "AM";
                            }else{
                                selectedHour -= 12;
                                MM = "PM";
                            }
                            if(selectedHour == 0){
                                selectedHour = 12;
                            }
                            String minutes = "";
                            if(selectedMinute < 10){
                                minutes = "0" + selectedMinute;
                            }
                            else{
                                minutes = selectedMinute + "";
                            }
                            fromTime.setText( selectedHour + ":" + minutes + " " + MM);
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.show();
                }
            }
        });
        final EditText untilTime = (EditText)findViewById(R.id.until_time);
        untilTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AddAttendance.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String MM = "AM";
                            if(selectedHour < 12){
                                MM = "AM";
                            }else{
                                selectedHour -= 12;
                                MM = "PM";
                            }
                            if(selectedHour == 0){
                                selectedHour = 12;
                            }
                            String minutes = "";
                            if(selectedMinute < 10){
                                minutes = "0" + selectedMinute;
                            }
                            else{
                                minutes = selectedMinute + "";
                            }
                            untilTime.setText( selectedHour + ":" + minutes + " " + MM);
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.show();
                }
            }
        });
    }

    public void DaysOfTheWeek(){

        final Button toggleSunday = (Button)findViewById(R.id.toggle_sunday);
        toggleSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sundayIsChecked){
                    toggleSunday.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
                    sundayIsChecked = false;
                } else{
                    toggleSunday.setBackground(getResources().getDrawable(R.drawable.roundedbutton_active));
                    sundayIsChecked = true;
                }
            }
        });

        final Button toggleMonday = (Button)findViewById(R.id.toggle_monday);
        toggleMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mondayIsChecked){
                    toggleMonday.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
                    mondayIsChecked = false;
                } else{
                    toggleMonday.setBackground(getResources().getDrawable(R.drawable.roundedbutton_active));
                    mondayIsChecked = true;
                }
            }
        });

        final Button toggleTuesday = (Button)findViewById(R.id.toggle_tuesday);
        toggleTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tuesdayIsChecked){
                    toggleTuesday.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
                    tuesdayIsChecked = false;
                } else{
                    toggleTuesday.setBackground(getResources().getDrawable(R.drawable.roundedbutton_active));
                    tuesdayIsChecked = true;
                }
            }
        });

        final Button toggleWednesday = (Button)findViewById(R.id.toggle_wednesday);
        toggleWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wednesdayIsChecked){
                    toggleWednesday.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
                    wednesdayIsChecked = false;
                } else{
                    toggleWednesday.setBackground(getResources().getDrawable(R.drawable.roundedbutton_active));
                    wednesdayIsChecked = true;
                }
            }
        });

        final Button toggleThursday = (Button)findViewById(R.id.toggle_thursday);
        toggleThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thursdayIsChecked){
                    toggleThursday.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
                    thursdayIsChecked = false;
                } else{
                    toggleThursday.setBackground(getResources().getDrawable(R.drawable.roundedbutton_active));
                    thursdayIsChecked = true;
                }
            }
        });

        final Button toggleFriday = (Button)findViewById(R.id.toggle_friday);
        toggleFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fridayIsChecked){
                    toggleFriday.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
                    fridayIsChecked = false;
                } else{
                    toggleFriday.setBackground(getResources().getDrawable(R.drawable.roundedbutton_active));
                    fridayIsChecked = true;
                }
            }
        });

        final Button toggleSaturday = (Button)findViewById(R.id.toggle_saturday);
        toggleSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saturdayIsChecked){
                    toggleSaturday.setBackground(getResources().getDrawable(R.drawable.roundedbutton));
                    saturdayIsChecked = false;
                } else{
                    toggleSaturday.setBackground(getResources().getDrawable(R.drawable.roundedbutton_active));
                    saturdayIsChecked = true;
                }
            }
        });



    }

}
