package com.omi.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.adapter.CircleAdapter;
import com.omi.app.adapter.RVAdapter2;
import com.omi.app.models.ImageModel;

public class LaundryServiceActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_service);

        getSupportActionBar().hide();
        tv1 = (TextView) findViewById(R.id.laundrytext);
        tv1.setText(Html.fromHtml("<u>Laundry Services</u> "));
        tv2 = (TextView) findViewById(R.id.hometext);
        tv2.setText(Html.fromHtml("<u>Home Services</u> "));

        tv3 = (TextView) findViewById(R.id.whtnew);
        tv3.setText(Html.fromHtml("<u>What's New</u> "));


        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LaundryServiceActivity.this, WhatsNewActivity.class);
                startActivity(i);
            }
        });

        // tv3 = (TextView) findViewById(R.id.comingsoon);
        // tv3.setText(Html.fromHtml("<u>Coming Soon</u> "));

        ImageModel[] myListData1 = new ImageModel[]{
                new ImageModel(R.drawable.laundry2, "Dry Cleaning"),
                new ImageModel(R.drawable.raj, "Wash and Iron"),
                new ImageModel(R.drawable.washfold, "Wash and fold"),

        };


        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.laundryrv);
        // recyclerView2.addItemDecoration(new DividerItemDecoration(this, 0));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(horizontalLayoutManager1);
        CircleAdapter adapter1 = new CircleAdapter(myListData1);
        recyclerView2.setAdapter(adapter1);


        ImageModel[] myListData2 = new ImageModel[]{
                new ImageModel(R.drawable.electrician, "Electrician"),
                new ImageModel(R.drawable.plumber, "plumber"),
                new ImageModel(R.drawable.car, "carpenter"),

        };


        RecyclerView rvhome = (RecyclerView) findViewById(R.id.homeserv);
        // recyclerView2.addItemDecoration(new DividerItemDecoration(this, 0));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvhome.setLayoutManager(horizontalLayoutManager2);
        RVAdapter2 adapter2 = new RVAdapter2(myListData2);
        rvhome.setAdapter(adapter2);


    }


}

