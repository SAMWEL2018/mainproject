package com.example.mainproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mainproject.R;
import com.google.android.material.textfield.TextInputEditText;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.PaperCommons.USERMAIL;
import static com.example.mainproject.Commons.PaperCommons.USERNAME;

public class Admin extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Administrator");

        username= findViewById(R.id.admin_name);
        password= findViewById(R.id.admin_password);
        log= findViewById(R.id.admin_login);
        Paper.init(Admin.this);
        final String user="ADMIN";
        final String mail="admin@gmail.com";

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(Admin.this);
                progressDialog.setMessage("please wait");

                String name=username.getText().toString();
                String pass= password.getText().toString();

                if (TextUtils.isEmpty(name.trim()) || TextUtils.isEmpty(pass.trim()) ){
                    username.setError("Enter username");
                    password.setError("Enter password");
                    progressDialog.dismiss();
                }
                else if (name.contains("admin") && pass.contains("admin")){

                    Paper.book().write(USERNAME,user);
                    Paper.book().write(USERMAIL,mail);
                    startActivity(new Intent(Admin.this,Admin_Dashboard.class));
                    finish();

                }
                else {
                    Toast.makeText(Admin.this, "Invalid details", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        });



    }
}
