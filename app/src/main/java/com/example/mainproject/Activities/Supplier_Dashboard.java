package com.example.mainproject.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.example.mainproject.MyAdapters.Tabadapter;
import com.example.mainproject.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import io.paperdb.Paper;

public class Supplier_Dashboard extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem add,check;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier__dashboard);

        tabLayout= findViewById(R.id.tablayout);
        add= findViewById(R.id.add_item);
        check= findViewById(R.id.check);
        viewPager= findViewById(R.id.viewpager);
        pagerAdapter= new Tabadapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(Supplier_Dashboard.this);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit the app");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Paper.book().destroy();
                ProgressDialog progressDialog=new ProgressDialog(Supplier_Dashboard.this);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                Intent intent=new Intent(Supplier_Dashboard.this,MainActivity.class);

                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        Button positive=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setTextColor(Color.RED);
        Button negative=alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setTextColor(Color.GREEN);

    }
}
