package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.Links.CHANGE_NAME;
import static com.example.mainproject.Commons.PaperCommons.USERMAIL;
import static com.example.mainproject.Commons.PaperCommons.USERNAME;

public class Account_settings extends AppCompatActivity {
    TextInputEditText change_name,change_pass;
    Button change,chang;
    static String TAG="";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        change_name=findViewById(R.id.change_username);
        change_pass=findViewById(R.id.change_password);
        chang=findViewById(R.id.chang);
        change=findViewById(R.id.change);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid());

        chang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=change_pass.getText().toString();
                if (TextUtils.isEmpty(password.trim())){
                    change_pass.setError("Field cant be empty");
                }
                else if (password.length()<6){
                    change_pass.setError("password should exceed 6 characters");
                }
                else {
                    firebaseUser.updatePassword(password)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Account_settings.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                    change_pass.setText("");
                                }
                            });
                }
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name=change_name.getText().toString();

                if (TextUtils.isEmpty(name.trim())){
                    change_name.setError("Field can't be empty");
                }
                else{

                    HashMap hashMap=new HashMap();
                    hashMap.put("username",name);

                    databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                change_name.setText("");
                                Toast.makeText(Account_settings.this, "Username Changed", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Account_settings.this, "Please Try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

    }
}
