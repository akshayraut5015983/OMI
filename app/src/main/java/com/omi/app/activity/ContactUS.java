package com.omi.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.utils.ErrorObject;

public class ContactUS extends AppCompatActivity implements RetrofitListener {
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conatct);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        textView = findViewById(R.id.txt_main);
        textView.setText(Html.fromHtml("We are happy to hear from you.\n" + "<br>" +
                "<b>" + "support@omionline.in" + "<br>" +
                "info@omionline.in" + "<br>" +
                "+91-6200567525" + "</b>"));
    }


    public void backimage(View view) {
        finish();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {

    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {

    }
}
