package com.example.mainproject.MyAdapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mainproject.fragments.Check_sup_individual_items;
import com.example.mainproject.fragments.Check_sup_item;
import com.example.mainproject.fragments.add_sup_item;

public class Tabadapter extends FragmentPagerAdapter {
    private  int tabs;

    public Tabadapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs=tabs;
    }






    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new add_sup_item();

            case 1:
                return  new Check_sup_individual_items();

            case 2:
                return new Check_sup_item();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
