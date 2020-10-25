package com.example.mainproject.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.Activities.Date_and_Time;
import com.example.mainproject.MyModels.Admin_event_model;
import com.example.mainproject.MyModels.event_model;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.mainproject.Commons.Links.EVENT_POST;

/**
 * A simple {@link Fragment} subclass.
 */
public class add extends Fragment {
    Button button;
    TextInputEditText name,location,contact,description,venue,date;
    public static final int CODE=1;
    Button get_date,get_time;
    TextView display_date,display_time;
    DatePickerDialog.OnDateSetListener listen;
    TimePickerDialog.OnTimeSetListener listening;
    DatabaseReference databaseReference;

    public add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference("Events");
        name=view.findViewById(R.id.event_name);
        location=view.findViewById(R.id.event_location);
        contact= view.findViewById(R.id.event_contact);
        description= view.findViewById(R.id.event_desc);
        venue=view.findViewById(R.id.event_venue);
        button= view.findViewById(R.id.event_set);
        get_date=view.findViewById(R.id.get_date);
        display_date=view.findViewById(R.id.disp_date);
        get_time=view.findViewById(R.id.get_time);
        display_time=view.findViewById(R.id.disp_time);

        get_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();

                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);
                TimePickerDialog dialog=new TimePickerDialog(getContext(),listening,hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                dialog.show();

            }
        });

        listening=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM;
                if (hourOfDay<12){
                    AM_PM="AM";
                }
                else {
                    AM_PM="PM";
                }
                String time= hourOfDay+" : "+minute+"  "+AM_PM;
                display_time.setText(time);

            }
        };

        get_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,listen,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        listen=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                display_date.setText(date);

            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String e_name=name.getText().toString();
                final String e_location= location.getText().toString();
                final String e_contact= contact.getText().toString();
                final String e_description=description.getText().toString();
                final String e_venue=venue.getText().toString();

                if (TextUtils.isEmpty(e_name.trim()) || TextUtils.isEmpty(e_location.trim()) || TextUtils.isEmpty(e_contact.trim())
                || TextUtils.isEmpty(e_description.trim()) || TextUtils.isEmpty(e_venue.trim())){
                    name.setError("Enter event name");
                    location.setError("Enter event location");
                    contact.setError("Enter Contact information");
                    description.setError("Enter event description");
                    venue.setError("Enter event venue");

                }
                else if(TextUtils.isEmpty(e_name.trim())){
                    name.setError("Enter event name");
                }
                else if(TextUtils.isEmpty(e_location.trim())){
                    location.setError("Enter event location");
                }
                else if(TextUtils.isEmpty(e_contact.trim())){
                    contact.setError("Enter contact information");
                }
                else if(TextUtils.isEmpty(e_description.trim())){
                    description.setError("Enter event description");
                }
                else if(TextUtils.isEmpty(e_venue.trim())){
                    venue.setError("Enter event venue");
                }
                else {
                   String date= display_time.getText().toString();
                   String time= display_date.getText().toString();
                    Admin_event_model event_model=new Admin_event_model(e_name,e_location,e_contact,e_description,e_venue,date,time);
                    databaseReference.push().setValue(event_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "Event Added", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getContext(), "Try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });


        return view;
    }


}
