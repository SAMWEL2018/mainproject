package com.example.mainproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainproject.R;

public class Documentation extends AppCompatActivity {
    TextView mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);
        mail=findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","samwelwafula2018@gmail.com",
                        null));
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                if (intent.resolveActivity(getPackageManager())!=null){
                    startActivity(Intent.createChooser(intent,"choose"));
                }

            }
        });

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Documentation");
    }

}
