package com.example.mainproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mainproject.R;
import com.example.mainproject.fragments.Login;

public class Admin_Dashboard extends AppCompatActivity {

    LinearLayout chats;
    LinearLayout events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__dashboard);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("DASHBOARD");

        chats= findViewById(R.id.chats);
        events= findViewById(R.id.event);

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Dashboard.this,Add_Event.class));
            }
        });

        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Dashboard.this,Admin_complaints.class));
            }
        });



    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(Admin_Dashboard.this);
        builder.setTitle("Quit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(Admin_Dashboard.this, MainActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        Button negative=alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setTextColor(Color.GREEN);
        Button positive=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setTextColor(Color.RED);
    }
}
