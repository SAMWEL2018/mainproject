package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyModels.FarmerComplaintmodel;
import com.example.mainproject.R;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.mainproject.Commons.Links.COMPLAINT;

public class Complaint extends AppCompatActivity {
    TextView date,time;
    Spinner spinner;
    String[] collection={"crop","livestock","Farming tools","Poultry","Fish farming" };
    EditText textarea;
    Button btnpost;
    static String TAG = "ms";

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Complaint");

        spinner= findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(Complaint.this,android.R.layout.simple_dropdown_item_1line,collection);
        spinner.setAdapter(arrayAdapter);

        textarea= findViewById(R.id.complaint_text_area);
        btnpost=findViewById(R.id.btn_post);
        date= findViewById(R.id.date);
        time=findViewById(R.id.time);
        databaseReference= FirebaseDatabase.getInstance().getReference("Complaints");

//      generate current date and time
        final Date dates= new Date();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        final String mydate= simpleDateFormat.format(dates);
//        date.setText(mydate);

        SimpleDateFormat timeformat= new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        final String mytime= timeformat.format(dates);
//        time.setText(mytime);

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner.getSelectedItem()==null){
                    Toast.makeText(Complaint.this, "Choose category", Toast.LENGTH_SHORT).show();
                }
                else {

                    final String complaint_text = textarea.getText().toString();
                    final String complaint_category = spinner.getSelectedItem().toString();
                    final String complaint_date= mydate;
                    final String complaint_time= mytime;
                    String id=databaseReference.push().getKey();

                    FarmerComplaintmodel farmerComplaintmodel=new FarmerComplaintmodel(complaint_category,complaint_text,complaint_date,complaint_time);
                    databaseReference.child(id).setValue(farmerComplaintmodel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    textarea.setText("");
                                    Toast.makeText(Complaint.this, "Complaint posted", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

    }
}
