package com.example.mainproject.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.example.mainproject.Activities.Documentation;
import com.example.mainproject.Activities.Register;
import com.example.mainproject.Activities.sells;
import com.example.mainproject.Activities.supplier_reg;
import com.example.mainproject.R;

public class Home extends Fragment {

    Intent intent,intent1,intent2,intent3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.home_fragment,container,false);

            intent= new Intent(getActivity(), Register.class);
            intent1= new Intent(getActivity(), supplier_reg.class);
            intent2= new Intent(getActivity(), Documentation.class);
            intent3=new Intent(getActivity(), sells.class);
            final CardView sells=view.findViewById(R.id.sells_card);
            sells.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent3);
                }
            });
            final CardView ln= view.findViewById(R.id.suppliers_account);
            ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent1);
                }
            });
            final CardView linearLayout=  view.findViewById(R.id.farmers);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
            final  CardView doc= view.findViewById(R.id.docs);
            doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(),view.findViewById(R.id.docu),"doc");
                    startActivity(intent2,activityOptionsCompat.toBundle());
                }
            });



        return  view;
    }
}
