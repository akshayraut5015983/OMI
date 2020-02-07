package com.omi.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public OrderFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    public OrderFragmentPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void addFragment(Fragment f) {
        fragments.add(f);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if (position==0){
            return "Restaurant";
        }else {
            return "Laundry";
        }

    }
}
