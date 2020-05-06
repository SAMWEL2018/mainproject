package com.example.mainproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.mainproject.Commons.Links.REGISTER;

public class Register extends AppCompatActivity {
    TextInputEditText email,username,password,confirm_password;
    Button reg;
    static String TAG = "ms";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email= findViewById(R.id.farmer_register_email);
        username= findViewById(R.id.farmer_register_username);
        password=findViewById(R.id.farmer_register_password);
        confirm_password= findViewById(R.id.farmer_register_confirm_password);
        reg= findViewById(R.id.btn_register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final  String email1= email.getText().toString();
                final  String username1= username.getText().toString();
                final  String password1= password.getText().toString();
                final  String confirm_password1= confirm_password.getText().toString();
                if (TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(username.getText().toString().trim())|| TextUtils.isEmpty(password.getText().toString().trim())|| TextUtils.isEmpty(confirm_password.getText().toString().trim())){

                    email.setError("Enter your email");
                    username.setError("Enter your username");
                    password.setError("Enter your password");
                    confirm_password.setError("Field cant be empty");


                }
                else if (!EmailValodator(email.getText().toString())){
                    email.setError("Enter your valid email");

                }

                else {

                    StringRequest sr = new StringRequest(Request.Method.POST, REGISTER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response != null) {
                                if (!response.isEmpty()) {
                                    if (response.equals("success")) {
                                        Toast.makeText(Register.this, "Registered successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, Login.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(Register.this, "authethication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.d(TAG, "onErrorResponse: myerror" + error);
                                    Toast.makeText(Register.this, "myerror " + error, Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> mymap = new HashMap<>();
                            mymap.put("email", email1);
                            mymap.put("username", username1);
                            mymap.put("password", password1);
                            return mymap;

                        }
                    };


                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                    requestQueue.add(sr);
                }


            }

        });
    }
    private void login() {
        if (TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(username.getText().toString().trim())|| TextUtils.isEmpty(password.getText().toString().trim())|| TextUtils.isEmpty(confirm_password.getText().toString().trim())){

            email.setError("Enter your email");
            username.setError("Enter your username");
            password.setError("Enter your password");
            confirm_password.setError("Field cant be empty");

        }
        else if (!EmailValodator(email.getText().toString())){
            email.setError("Enter your valid email");
        }

    }


    public boolean EmailValodator(String Email){

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN= "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern= Pattern.compile(EMAIL_PATTERN);
        matcher= pattern.matcher(Email);
        return  matcher.matches();
    }

    public void rdlogin(View view) {
        Intent intent= new Intent(Register.this,Login.class);
        startActivity(intent);
    }
}
