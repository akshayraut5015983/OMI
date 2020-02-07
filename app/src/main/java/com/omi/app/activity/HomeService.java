package com.omi.app.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.HomeServiceAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CityItem;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.HomeServiceItem;
import com.omi.app.models.HomeServiceResponce;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.CITY_LOCATION;
import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LONGI;

public class HomeService extends AppCompatActivity implements RetrofitListener {
    private ApiServiceProvider apiServiceProvider;
    private RecyclerView recyclerView;
    String id = "", cityId = "0";
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);


        apiServiceProvider = ApiServiceProvider.getInstance(this);
        recyclerView = findViewById(R.id.rcylerview);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            id = extra.getString("id");
        }
        String lat = (String) SharePreferenceUtility.getPreferences(HomeService.this, LAT, SharePreferenceUtility.PREFTYPE_STRING);
        String longi = (String) SharePreferenceUtility.getPreferences(HomeService.this, LONGI, SharePreferenceUtility.PREFTYPE_STRING);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (NetworkUtils.isNetworkConnected(HomeService.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            dialog.show();
            apiServiceProvider.getRequestHomeService(lat,longi, id, HomeService.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }
    }

    public void backimage(View view) {
        finish();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof HomeServiceResponce) {
            HomeServiceResponce serviceResponce = (HomeServiceResponce) responseBody;
            if (serviceResponce.flag) {
                List<HomeServiceItem> serviceItems = ((HomeServiceResponce) responseBody).data;

                HomeServiceAdapter listAdapter = new HomeServiceAdapter(serviceItems, HomeService.this);
                recyclerView.setAdapter(listAdapter);
//                Toast.makeText(this, "Find", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Oops...Service not available near you kindly explore with other location");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

}
