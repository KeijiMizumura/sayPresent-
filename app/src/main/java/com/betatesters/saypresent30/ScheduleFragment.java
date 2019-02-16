package com.betatesters.saypresent30;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleFragment extends Fragment {

    DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHandler = new DatabaseHandler(getContext());

        Calendar nextYear = Calendar.getInstance();

        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView)view.findViewById(R.id.calenderView);

        Date today = new Date();
        calendar.init(today, nextYear.getTime()).withSelectedDate(today);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String dayOfWeek = (String) android.text.format.DateFormat.format("EEEE", date);
                String month = (String) android.text.format.DateFormat.format("MM", date);
                String day = (String) android.text.format.DateFormat.format("dd", date);
                String year = (String) android.text.format.DateFormat.format("yyyy", date);



                int monthInt = Integer.parseInt(month) - 1;
                int dayInt = Integer.parseInt(day);
                int yearInt = Integer.parseInt(year);
                int dayOfWeekInt = 0;

                switch(dayOfWeek){
                    case "Sunday":
                        dayOfWeekInt = 1;
                        break;
                    case "Monday":
                        dayOfWeekInt = 2;
                        break;
                    case "Tuesday":
                        dayOfWeekInt = 3;
                        break;
                    case "Wednesday":
                        dayOfWeekInt = 4;
                        break;
                    case "Thursday":
                        dayOfWeekInt = 5;
                        break;
                    case "Friday":
                        dayOfWeekInt = 6;
                        break;
                    case "Saturday":
                        dayOfWeekInt = 7;
                        break;
                }

                Intent i = new Intent(getActivity(),ScheduleActivity.class);
                i.putExtra("MONTH", monthInt);
                i.putExtra("DAY", dayInt);
                i.putExtra("WEEK", dayOfWeekInt);
                i.putExtra("YEAR", yearInt);
                startActivity(i);

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });


//
//        CalendarView calendarView = view.findViewById(R.id.calendarView);
//
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month,
//                                            int dayOfMonth) {
//                Calendar calendar = Calendar.getInstance();
//
//                calendar.set(year, month, dayOfMonth);
//
//                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//
//                Intent i = new Intent(getActivity(),ScheduleActivity.class);
//                i.putExtra("MONTH", month);
//                i.putExtra("DAY", dayOfMonth);
//                i.putExtra("WEEK", dayOfWeek);
//                i.putExtra("YEAR", year);
//                startActivity(i);
//
//
//            }
//        });

    }
}
