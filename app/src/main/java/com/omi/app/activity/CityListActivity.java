package com.omi.app.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.CityListAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.models.CityItem;
import com.omi.app.models.CityResponce;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;

import java.util.List;

public class CityListActivity extends AppCompatActivity implements RetrofitListener {

    private RecyclerView mRvCityList;
    private ApiServiceProvider apiServiceProvider;
    private Dialog dialog;
    private boolean IS_FROM_CHECKOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        mRvCityList = findViewById(R.id.rv_city_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        apiServiceProvider = ApiServiceProvider.getInstance(this);

        if (getIntent() != null) {
            if (getIntent().hasExtra("from_checkout")) {
                IS_FROM_CHECKOUT = getIntent().getExtras().getBoolean("from_checkout");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvCityList.setLayoutManager(layoutManager);

        if (NetworkUtils.isNetworkConnected(CityListActivity.this)) {
            dialog.show();
            apiServiceProvider.getRequestCityList(CityListActivity.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof CityResponce) {
            List<CityItem> cityItems = ((CityResponce) responseBody).data;
            CityListAdapter cityListAdapter = new CityListAdapter(cityItems, CityListActivity.this, IS_FROM_CHECKOUT);
            mRvCityList.setAdapter(cityListAdapter);
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        Toast.makeText(this, errorObject.getDeveloperMessage(), Toast.LENGTH_SHORT).show();
    }

    public void backimage(View view) {
        finish();
    }
}
