package com.omi.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.omi.app.R;
import com.omi.app.adapter.LaundryAdpater;
import com.omi.app.models.Laundry;

public class Cart2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        getSupportActionBar().hide();
        Laundry[] myListData = new Laundry[] {
                new Laundry("shirt","Wash/iron","20","child",1)
                ,new Laundry("shirt","Wash/iron","20","child",1),
                new Laundry("shirt","Wash/iron","20","child",1)
                ,new Laundry("shirt","Wash/iron","20","child",1),
                new Laundry("shirt","Wash/iron","20","child",1),

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_laundry_menu);
        LaundryAdpater adapter = new LaundryAdpater(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Cart2Activity.this, CartListActivity.class);
                startActivity(i);
            }
        });
    }
}
