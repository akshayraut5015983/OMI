package com.omi.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.omi.app.R;

public class OrderCompletionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_completion);
        getSupportActionBar().hide();

        LinearLayout linew = findViewById(R.id.liner);
        linew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(OrderCompletionActivity.this,OrderActivity.class);
                startActivity(i);
            }
        });
    }
}
