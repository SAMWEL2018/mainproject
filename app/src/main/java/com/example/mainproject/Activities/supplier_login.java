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
import com.example.mainproject.MyModels.Supplier_Register_model;
import com.example.mainproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.Links.SUP_LOG;

public class supplier_login extends AppCompatActivity {

    TextInputEditText email,passw;
    Button lg;
    static String TAG = "ms";
    Snackbar snackbar;
    DatabaseReference databaseReference;
    public static final String SUP="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_login);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Login");
        databaseReference= FirebaseDatabase.getInstance().getReference("Custom_Suppliers_Accounts");

        email= findViewById(R.id.sup_email);
        passw= findViewById(R.id.sup_passw);
        lg= findViewById(R.id.supp_login);
        Paper.init(this);

        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog= new ProgressDialog(supplier_login.this);
                progressDialog.setMessage("please wait...");
                progressDialog.show();


                final String email1= email.getText().toString();
                final String password= passw.getText().toString();

                if (TextUtils.isEmpty(email1.trim()) || TextUtils.isEmpty(password.trim())){

                    email.setError("enter email");
                    passw.setError("enter password");
                    progressDialog.dismiss();
                }
                else {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data:dataSnapshot.getChildren()){
                                Supplier_Register_model reg=data.getValue(Supplier_Register_model.class);
                                
                                if (reg.getName().equals(email1) && reg.getPassword().equals(password)){
                                    Paper.book().write(SUP,email1);
                                    startActivity(new Intent(supplier_login.this,Supplier_Dashboard.class));
                                    finish();
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(supplier_login.this, "Invalid details", Toast.LENGTH_SHORT).show();
                                    email.setText("");
                                    passw.setText("");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(supplier_login.this, "Please Try again later", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        });





    }

    public  void rdsupprege(View view){
        startActivity(new Intent(supplier_login.this,supplier_reg.class));
        finish();

    }
}
