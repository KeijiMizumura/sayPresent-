package com.betatesters.saypresent30;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Table extends AppCompatActivity {

    private ListView listView;
    private MonthListAdapter monthListAdapter;
    private DatabaseHandler databaseHandler;
    private int SubjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        databaseHandler = new DatabaseHandler(this);



    }
}
