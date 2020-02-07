package com.omi.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.omi.app.R;

public class ChangePasswordAcivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void backimage(View view) {
        finish();
    }
}
