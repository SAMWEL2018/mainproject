package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_Password extends AppCompatActivity {
    EditText mail;
    Button reset;
    FirebaseAuth firebaseAuth;
    TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);
        mail=findViewById(R.id.reset_mail);
        reset=findViewById(R.id.reset);
        firebaseAuth=FirebaseAuth.getInstance();
        reg=findViewById(R.id.create);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reset_Password.this,Register.class));
                finish();
            }
        });

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Reset Account Password");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mail.getText().toString();

                if (TextUtils.isEmpty(email.trim())){
                    mail.setError("Enter Your Account Email");
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Reset_Password.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Reset_Password.this,Login.class));
                                finish();
                            }
                            else {
                                String error=task.getException().getMessage();

                                if (task.getException().getMessage().equals(error)){
                                    Toast.makeText(Reset_Password.this, "Account doesn't exist,create account ", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(Reset_Password.this, "Please Try again later", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

    }
}
