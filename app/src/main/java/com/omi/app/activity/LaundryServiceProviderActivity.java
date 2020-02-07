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
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.adapter.LaundryServiceProviderAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartItemLoundry;
import com.omi.app.models.CartListLoundryResponce;
import com.omi.app.models.LoginItems;
import com.omi.app.models.SubServiceLoundryItem;
import com.omi.app.models.SubServiceLoundryResponce;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;
import static com.omi.app.utils.Constants.AppConst.LONGI;

public class LaundryServiceProviderActivity extends AppCompatActivity implements RetrofitListener {
    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRecyclerView;
    String service_id = "", name = "", city_id = "";
    private Dialog dialog;
    private LoginItems loginItems;
    private List<CartItemLoundry> arrItemCartDetails;
    private CartListLoundryResponce cartListLoundryResponce;
    String lat;
    String longi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_subserviceb);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lat = (String) SharePreferenceUtility.getPreferences(LaundryServiceProviderActivity.this, LAT, SharePreferenceUtility.PREFTYPE_STRING);
        longi = (String) SharePreferenceUtility.getPreferences(LaundryServiceProviderActivity.this, LONGI, SharePreferenceUtility.PREFTYPE_STRING);

        apiServiceProvider = ApiServiceProvider.getInstance(this);
        mRecyclerView = findViewById(R.id.rcylersubservice);
        TextView tvName = findViewById(R.id.tv_name);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            service_id = extras.getString("key");
            name = extras.getString("name");
            city_id = extras.getString("city_id");
        }
        tvName.setText(name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.isNetworkConnected(LaundryServiceProviderActivity.this)) {
            dialog.show();
            apiServiceProvider.getRequestCartListLoundry(loginItems.id, LaundryServiceProviderActivity.this);
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
        if (responseBody instanceof SubServiceLoundryResponce) {
            SubServiceLoundryResponce serviceResponce = (SubServiceLoundryResponce) responseBody;
            if (serviceResponce.flag) {
                List<SubServiceLoundryItem> serviceItems = ((SubServiceLoundryResponce) responseBody).data;
                if (cartListLoundryResponce != null) {
                    LaundryServiceProviderAdapter listAdapter = new LaundryServiceProviderAdapter(serviceItems, LaundryServiceProviderActivity.this, cartListLoundryResponce.data.dhobiInfo.id);
                    mRecyclerView.setAdapter(listAdapter);
                } else {
                    LaundryServiceProviderAdapter listAdapter = new LaundryServiceProviderAdapter(serviceItems, LaundryServiceProviderActivity.this);
                    mRecyclerView.setAdapter(listAdapter);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No Data Available");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            }
        } else if (responseBody instanceof CartListLoundryResponce) {
            cartListLoundryResponce = (CartListLoundryResponce) responseBody;
            arrItemCartDetails = cartListLoundryResponce.data.itemInfo;
            apiServiceProvider.getRequestSubServiceLoundry(lat, longi, city_id, LaundryServiceProviderActivity.this);
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        if (throwable.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 23 path $.data")) {
            apiServiceProvider.getRequestSubServiceLoundry(lat, longi, city_id, LaundryServiceProviderActivity.this);
        } else {
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
}
