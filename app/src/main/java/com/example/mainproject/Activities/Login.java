package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.Links.LOGIN;
import static com.example.mainproject.Commons.PaperCommons.USERMAIL;
import static com.example.mainproject.Commons.PaperCommons.USERNAME;

public class Login extends AppCompatActivity {
    Button login;
    EditText email,password;
    static String TAG = "ms";
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView reset;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth=FirebaseAuth.getInstance();
        login= findViewById(R.id.btn_login);
        email=findViewById(R.id.farmer_login_email);
        password=findViewById(R.id.farmer_login_password);
        reset=findViewById(R.id.reset_password);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Reset_Password.class));
            }
        });


        Paper.init(Login.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog=new ProgressDialog(Login.this);
                dialog.setMessage("please wait...");
                dialog.setCancelable(false);
                dialog.show();

                final String email2 = email.getText().toString();
                final String password2 = password.getText().toString();

                if (TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(password.getText().toString().trim())) {
                    email.setError("Enter Email");
                    password.setError("enter password");
                    dialog.dismiss();
                } else {

                    firebaseAuth.signInWithEmailAndPassword(email2,password2)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        startActivity(new Intent(Login.this, FarmerDashBoard.class));
                                        finish();
                                    }
                                    else {
                                        dialog.dismiss();
                                        email.setText("");
                                        password.setText("");
                                        Toast.makeText(Login.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });
    }

    public void rdregister(View view) {
        Intent intent= new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();
    }
}
