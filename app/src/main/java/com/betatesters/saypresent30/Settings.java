package com.betatesters.saypresent30;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.zip.Inflater;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("sayPresent! Data");

        Button btn = (Button)findViewById(R.id.clear_data);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View myView = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setCancelable(true);
                builder.setTitle("Erase All?");
                builder.setMessage("Are you sure you want to delete everything?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getApplicationContext().deleteDatabase("Say_Present.db");
                                Snackbar snackbar = Snackbar.make(myView, "Deleted", Snackbar.LENGTH_SHORT);
                                snackbar.show();

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
