package com.omi.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.adapter.Pager2;
import com.omi.app.adapter.RVAdapter2;
import com.omi.app.models.ImageModel;

public class WhatsNewActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


    TextView tv1, tv2;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_new);

        getSupportActionBar().hide();

        tv1 = (TextView) findViewById(R.id.whtsnews);
        tv1.setText(Html.fromHtml("<u>What's New</u> "));

        tv2 = (TextView) findViewById(R.id.cmtext);
        tv2.setText(Html.fromHtml("<u>Coming Soon</u> "));


        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WhatsNewActivity.this, RestaturuntActivity.class);
                startActivity(i);
            }
        });


        ImageModel[] myListData1 = new ImageModel[]{
                new ImageModel(R.drawable.trave, "Traval"),
                new ImageModel(R.drawable.parlor, "Parlor"),
                new ImageModel(R.drawable.medical, "Medical Services"),

        };


        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.commingrv);
        // recyclerView2.addItemDecoration(new DividerItemDecoration(this, 0));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(horizontalLayoutManager1);
        RVAdapter2 adapter1 = new RVAdapter2(myListData1);
        recyclerView2.setAdapter(adapter1);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager1);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Men"));
        tabLayout.addTab(tabLayout.newTab().setText("Women"));
        tabLayout.addTab(tabLayout.newTab().setText("Children"));
        tabLayout.addTab(tabLayout.newTab().setText("HouseHold"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 30, 0);
            tab.requestLayout();
        }

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager1);

        //Creating our pager adapter
        Pager2 adapter = new Pager2(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    }

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
}