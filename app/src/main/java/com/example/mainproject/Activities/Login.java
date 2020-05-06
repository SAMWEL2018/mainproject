package com.example.mainproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.Links.LOGIN;
import static com.example.mainproject.Commons.PaperCommons.USERMAIL;

public class Login extends AppCompatActivity {
    Button login;
    EditText email,password;
    static String TAG = "ms";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login= findViewById(R.id.btn_login);
        email=findViewById(R.id.farmer_login_email);
        password=findViewById(R.id.farmer_login_password);

        Paper.init(Login.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email2 = email.getText().toString();
                final String password2 = password.getText().toString();
                if (TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(password.getText().toString().trim())) {
                    email.setError("Enter Email");
                    password.setError("enter password");
                } else {


                    StringRequest sr = new StringRequest(Request.Method.POST, LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                        Toast.makeText(FarmerRealLog.this, "hello" + response, Toast.LENGTH_SHORT).show();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String ans = jsonObject.getString("success");

                                if (ans.equals("success")) {
                                    Paper.book().write(USERMAIL,email2);
                                    Toast.makeText(Login.this, "login successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, FarmerDashBoard.class));
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Invalid details!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.d(TAG, "onErrorResponse: myerror" + error);
                                    Toast.makeText(Login.this, "myerror " + error, Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> mymap = new HashMap<>();
                            mymap.put("email", email2);
                            mymap.put("password", password2);
                            return mymap;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                    requestQueue.add(sr);

                }
            }
        });
    }

    public void rdregister(View view) {
        Intent intent= new Intent(Login.this, Register.class);
        startActivity(intent);
    }
}
