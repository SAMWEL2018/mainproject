package com.example.mainproject.MyAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mainproject.fragments.add;
import com.example.mainproject.fragments.check_event;

public class EventAdapter extends FragmentPagerAdapter {

     private int tab;

    public EventAdapter(@NonNull FragmentManager fm, int tab) {
        super(fm);
        this.tab = tab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new add();

            case 1:
                return new check_event();

            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return tab;
    }
}
