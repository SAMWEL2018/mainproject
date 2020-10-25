package com.example.mainproject.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.mainproject.R;
import com.example.mainproject.fragments.add;

import java.util.Calendar;

public class Date_and_Time extends AppCompatActivity {
    DatePicker datePicker;
    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_and__time);
        datePicker=findViewById(R.id.datepicker);
        Calendar calendar=Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Toast.makeText(Date_and_Time.this, ""+view.getDayOfMonth()+"-"+view.getDayOfMonth()+"-"+view.getYear(), Toast.LENGTH_SHORT).show();
                String rq=dayOfMonth+"-"+monthOfYear+"-"+year;
                Toast.makeText(Date_and_Time.this, ""+rq, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent("sam");
                intent.putExtra("key",rq);
                LocalBroadcastManager.getInstance(Date_and_Time.this).sendBroadcast(intent);
            }
        });
        Button button=findViewById(R.id.done);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Date_and_Time.this,add.class);
                intent.putExtra("EXTRA", "");

                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });
    }

//    public void Done(View view) {
//        FragmentManager fragmentManager=getFragmentManager();
//        FragmentTransaction fragmentTransaction=f
//        startActivity(new Intent(Date_and_Time.this,add.class));
//    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public void Done(View view) {
//            Intent intent=new Intent(Date_and_Time.this,add.class);
//            String date=datePicker.toString();
//            intent.putExtra("EXTRA", "date");
//            setResult(Activity.RESULT_OK,intent);
//            finish();
//    }
}
