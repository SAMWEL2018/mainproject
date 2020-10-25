package com.example.mainproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.mainproject.R;

public class Splash_page extends AppCompatActivity {
    private static int SPLASH_TIME=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash_page);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(Splash_page.this, MainActivity.class));
                finish();

            }
        },SPLASH_TIME);

    }
}
