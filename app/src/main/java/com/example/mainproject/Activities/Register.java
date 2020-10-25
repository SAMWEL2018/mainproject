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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyModels.FarmerRegistermodel;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.mainproject.Commons.Links.REGISTER;

public class Register extends AppCompatActivity {
    TextInputEditText email,username,password,confirm_password;
    Button reg;
    static String TAG = "ms";
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Register");

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        email= findViewById(R.id.farmer_register_email);
        username= findViewById(R.id.farmer_register_username);
        password=findViewById(R.id.farmer_register_password);
        confirm_password= findViewById(R.id.farmer_register_confirm_password);
        reg= findViewById(R.id.btn_register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog= new ProgressDialog(Register.this);
                progressDialog.setTitle("Registering account");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                final  String email1= email.getText().toString();
                final  String username1= username.getText().toString();
                final  String password1= password.getText().toString();
                final  String confirm_password1= confirm_password.getText().toString();
                if (TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(username.getText().toString().trim())|| TextUtils.isEmpty(password.getText().toString().trim())|| TextUtils.isEmpty(confirm_password.getText().toString().trim())){

                    email.setError("Enter your email");
                    username.setError("Enter your username");
                    password.setError("Enter your password");
                    confirm_password.setError("Field cant be empty");
                    progressDialog.dismiss();


                }
                else if (!EmailValodator(email.getText().toString())){
                    email.setError("Enter your valid email");
                    progressDialog.dismiss();

                }
                else if (password1.length()<6){
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "Combined password should exceed 6 characters", Toast.LENGTH_SHORT).show();
                    password.setError("Combined password should exceed 6 characters");
                }

                else if(!password1.equals(confirm_password1)){
                    Toast.makeText(Register.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                else {

                    firebaseAuth.createUserWithEmailAndPassword(email1,password1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        FarmerRegistermodel farmerRegistermodel=new FarmerRegistermodel(email1,username1);
                                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(farmerRegistermodel)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Toast.makeText(Register.this, "You are Registered", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Register.this, Login.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                        else {
                                                            Toast.makeText(Register.this, "Please Try again later", Toast.LENGTH_SHORT).show();
                                                            Toast.makeText(Register.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        Toast.makeText(Register.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });

                }


            }

        });
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
        finish();
    }
}
