package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.mainproject.MyModels.Supplier_Register_model;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.mainproject.Commons.Links.SUP_REG;

public class supplier_reg extends AppCompatActivity {

    TextInputEditText name,contact,pass;
    Button supp_reg;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_reg);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Registration");
        databaseReference= FirebaseDatabase.getInstance().getReference("Custom_Suppliers_Accounts");

        name= findViewById(R.id.supp_name);
        contact= findViewById(R.id.supp_contacts);
        pass= findViewById(R.id.supp_pass);
        supp_reg= findViewById(R.id.sup_reg);

        supp_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog= new ProgressDialog(supplier_reg.this);
                progressDialog.setTitle("Registering account");
                progressDialog.setMessage("please wait...");
                progressDialog.show();

                final String name1= name.getText().toString();
                final String contact1= contact.getText().toString();
                final String password= pass.getText().toString();

                if (TextUtils.isEmpty(name.getText().toString().trim()) || TextUtils.isEmpty(contact.getText().toString().trim()) || TextUtils.isEmpty(pass.getText().toString().trim())) {

                    name.setError("Enter your email");
                    contact.setError("Enter your username");
                    pass.setError("Enter your password");
                    progressDialog.dismiss();

                } else if (!EmailValodator(name.getText().toString())){
                    name.setError("Use Your Valid Email");
                    progressDialog.dismiss();
                }
                else if(password.length()<6){
                    progressDialog.dismiss();
                    Toast.makeText(supplier_reg.this, "Combined password should exceed 6 characters", Toast.LENGTH_SHORT).show();
                    pass.setError("Combined password should exceed 6 characters");
                }
                else {
                    Supplier_Register_model reg=new Supplier_Register_model(name1,password,contact1);
                    databaseReference.push().setValue(reg)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()){
                                        Toast.makeText(supplier_reg.this, "You are Registered", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(supplier_reg.this,supplier_login.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(supplier_reg.this, "Please Try again later", Toast.LENGTH_SHORT).show();
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




    public void rdsuplog(View view) {
        startActivity(new Intent(supplier_reg.this,supplier_login.class));
        finish();
    }
}
