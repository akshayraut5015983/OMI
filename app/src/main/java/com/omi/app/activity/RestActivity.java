package com.omi.app.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.omi.app.R;
import com.omi.app.adapter.RestureneListAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartListResponce;
import com.omi.app.models.LoginItems;
import com.omi.app.models.ResturenListItem;
import com.omi.app.models.ResturentListResponce;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;
import static com.omi.app.utils.Constants.AppConst.LONGI;

public class RestActivity extends AppCompatActivity implements RetrofitListener {
    private RecyclerView mRvCityList;
    private ApiServiceProvider apiServiceProvider;
    String city_id = "0";
    private Dialog dialog;
    private CartListResponce cartListResponce;
    private LoginItems loginItems;
    private String lat, longi;
    private SearchView searchView;
    String serch = "";

    List<ResturenListItem> resturenListItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rstactivity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            city_id = extras.getString("city_id");
        }
        mRvCityList = findViewById(R.id.rv_rest_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        searchView = findViewById(R.id.ed_serch);
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(RestActivity.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvCityList.setLayoutManager(layoutManager);

        if (NetworkUtils.isNetworkConnected(RestActivity.this)) {
            dialog.show();
            apiServiceProvider.getRequestCartList(loginItems.id, RestActivity.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if(serviceItems.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(HomeActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }
                serch = query;
                dialog.show();
                apiServiceProvider.getRequestResturenList(lat, longi, city_id, serch, RestActivity.this);
//                Toast.makeText(ResturentListActivity.this, query+" "+serch, Toast.LENGTH_SHORT).show();
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof ResturentListResponce) {
            resturenListItems = ((ResturentListResponce) responseBody).data;
            if (resturenListItems.size() > 0) {
                if (cartListResponce.flag) {
                    RestureneListAdapter cityListAdapter = new RestureneListAdapter(resturenListItems, RestActivity.this, cartListResponce.restInfo.id);
                    mRvCityList.setAdapter(cityListAdapter);
                } else {
                    RestureneListAdapter cityListAdapter = new RestureneListAdapter(resturenListItems, RestActivity.this);
                    mRvCityList.setAdapter(cityListAdapter);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No Restaurants Available");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            }
        } else if (responseBody instanceof CartListResponce) {
            cartListResponce = (CartListResponce) responseBody;
            lat = (String) SharePreferenceUtility.getPreferences(RestActivity.this, LAT, SharePreferenceUtility.PREFTYPE_STRING);
            longi = (String) SharePreferenceUtility.getPreferences(RestActivity.this, LONGI, SharePreferenceUtility.PREFTYPE_STRING);
            apiServiceProvider.getRequestResturenList(lat, longi, city_id, serch, RestActivity.this);
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Oops...Restaurant name not find");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    public void backimage(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ResturentListActivity.class);
        startActivity(i);
    }

    public void serchImg(View view) {
        LinearLayout linearLayout = findViewById(R.id.lo);
        linearLayout.setVisibility(View.VISIBLE);
    }
}