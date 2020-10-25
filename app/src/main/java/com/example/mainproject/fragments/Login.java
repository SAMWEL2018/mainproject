package com.example.mainproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainproject.Activities.Admin;
import com.example.mainproject.Activities.Admin_Dashboard;
import com.example.mainproject.Activities.supplier_login;
import com.example.mainproject.Activities.supplier_reg;
import com.example.mainproject.R;

public class Login extends Fragment {
    Intent intent,intent1,intent3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.login_fragment,container,false);

        intent = new Intent(getActivity(), com.example.mainproject.Activities.Login.class);
        intent1= new Intent(getActivity(), supplier_login.class);
        intent3=new Intent(getActivity(), Admin.class);
        Button sup= view.findViewById(R.id.supply_account);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
        final Button button = (Button) view.findViewById(R.id.farm);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Button admin= view.findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });

        return  view;
    }
}
