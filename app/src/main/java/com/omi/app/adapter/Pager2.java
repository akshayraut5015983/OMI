package com.omi.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.omi.app.fragment.TabLaundry;

public class Pager2 extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager2(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                TabLaundry tab1 = new TabLaundry();
                return tab1;
            case 1:
                TabLaundry tab2 = new TabLaundry();
                return tab2;
            case 2:
                TabLaundry tab3 = new TabLaundry();
                return tab3;
            case 3:
                TabLaundry tab4 = new TabLaundry();
                return tab4;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}