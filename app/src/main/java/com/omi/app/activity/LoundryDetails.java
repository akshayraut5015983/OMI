package com.omi.app.activity;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.LoundryServiceAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartListLoundryResponce;
import com.omi.app.models.LoginItems;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.LoundryMenuResponce;
import com.omi.app.models.LoundryTypeItem;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class LoundryDetails extends AppCompatActivity implements RetrofitListener {
    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRecyclerView;
    private TextView tvMen, tvWomen, tvChild, tvHousehold, tvName;
    private String loundryId = "", typeOne = "", name = "";
    private LoundryServiceAdapter listAdapter;
    private List<LoundryTypeItem> serviceItems;
    private Dialog dialog;
    private RelativeLayout mRlCartDetails;
    private LoginItems loginItems;
    private CartListLoundryResponce cartListLoundryResponce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loundrydetails);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tvName = findViewById(R.id.tv_name);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            loundryId = extra.getString("l_id");
            typeOne = extra.getString("type");
            name = extra.getString("name");
        }
        tvName.setText(name);
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(LoundryDetails.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        mRlCartDetails = findViewById(R.id.rl_view_card);

        tvMen = findViewById(R.id.tv_men);
        tvWomen = findViewById(R.id.tv_women);
        tvChild = findViewById(R.id.tv_child);
        tvHousehold = findViewById(R.id.tv_hous);
        mRecyclerView = findViewById(R.id.rcylersubservice);
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        tvMen.setBackgroundResource(R.color.grey);
        tvMen.setTextColor(Color.parseColor("#FFFFFF"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);


        ((TextView) findViewById(R.id.view_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoundryDetails.this, CartListActivity.class);
                i.putExtra("from_laundry", true);
                startActivity(i);
            }
        });


        if (NetworkUtils.isNetworkConnected(LoundryDetails.this)) {
            dialog.show();
            apiServiceProvider.getRequestLoundryMenu(loundryId, typeOne, typeOne, LoundryDetails.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mRlCartDetails.setVisibility(View.GONE);
        if (NetworkUtils.isNetworkConnected(LoundryDetails.this)) {
            dialog.show();
            apiServiceProvider.getRequestCartListLoundry(loginItems.id, LoundryDetails.this);
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
        if (responseBody instanceof LoundryMenuResponce) {
            LoundryMenuResponce londryCardResponce = (LoundryMenuResponce) responseBody;
            if (londryCardResponce.flag) {

                serviceItems = ((LoundryMenuResponce) responseBody).data;
                mRecyclerView.setVisibility(View.VISIBLE);
                listAdapter = new LoundryServiceAdapter(serviceItems, LoundryDetails.this, apiServiceProvider, loundryId, dialog, typeOne);
                mRecyclerView.setAdapter(listAdapter);

            } else {
                Toast.makeText(this, "Else Part execute", Toast.LENGTH_SHORT).show();

            }
        } else if (responseBody instanceof CartListLoundryResponce) {
            cartListLoundryResponce = (CartListLoundryResponce) responseBody;
            if (cartListLoundryResponce.data.itemInfo.size() > 0) {
                mRlCartDetails.setVisibility(View.VISIBLE);
            } else {
                mRlCartDetails.setVisibility(View.GONE);
            }
            ((TextView) findViewById(R.id.tv_cart_details)).setText(String.valueOf(cartListLoundryResponce.data.itemInfo.size()) + "Item " + "| \u20B9 " + cartListLoundryResponce.priceDetails.totalAmount);
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();

    }

    public void men(View view) {
        if (serviceItems != null) {
            serviceItems.clear();
            mRecyclerView.setVisibility(View.GONE);
        }
        tvMen.setBackgroundResource(R.color.grey);
        tvWomen.setBackgroundResource(R.color.colorPrimaryDark);
        tvChild.setBackgroundResource(R.color.colorPrimaryDark);
        tvHousehold.setBackgroundResource(R.color.colorPrimaryDark);
        tvMen.setTextColor(Color.parseColor("#FFFFFF"));
        tvWomen.setTextColor(Color.parseColor("#303030"));
        tvChild.setTextColor(Color.parseColor("#303030"));
        tvHousehold.setTextColor(Color.parseColor("#303030"));

        String typeTwo = "1";
        if (NetworkUtils.isNetworkConnected(LoundryDetails.this)) {

            apiServiceProvider.getRequestLoundryMenu(loundryId, typeTwo, typeOne, LoundryDetails.this);

        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void women(View view) {
        if (serviceItems != null) {
            serviceItems.clear();
            mRecyclerView.setVisibility(View.GONE);
        }
        tvWomen.setTextColor(Color.parseColor("#FFFFFF"));
        tvMen.setBackgroundResource(R.color.colorPrimaryDark);
        tvWomen.setBackgroundResource(R.color.grey);
        tvChild.setBackgroundResource(R.color.colorPrimaryDark);
        tvHousehold.setBackgroundResource(R.color.colorPrimaryDark);
        tvWomen.setTextColor(Color.parseColor("#FFFFFF"));
        tvMen.setTextColor(Color.parseColor("#303030"));
        tvChild.setTextColor(Color.parseColor("#303030"));
        tvHousehold.setTextColor(Color.parseColor("#303030"));

        String typeTwo = "2";

        if (NetworkUtils.isNetworkConnected(LoundryDetails.this)) {

            apiServiceProvider.getRequestLoundryMenu(loundryId, typeTwo, typeOne, LoundryDetails.this);

        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void child(View view) {

        if (serviceItems != null) {
            serviceItems.clear();
            mRecyclerView.setVisibility(View.GONE);
        }
        tvMen.setBackgroundResource(R.color.colorPrimaryDark);
        tvWomen.setBackgroundResource(R.color.colorPrimaryDark);
        tvChild.setBackgroundResource(R.color.grey);
        tvHousehold.setBackgroundResource(R.color.colorPrimaryDark);
        tvChild.setTextColor(Color.parseColor("#FFFFFF"));
        tvWomen.setTextColor(Color.parseColor("#303030"));
        tvMen.setTextColor(Color.parseColor("#303030"));
        tvHousehold.setTextColor(Color.parseColor("#303030"));
        String typeTwo = "3";
        if (NetworkUtils.isNetworkConnected(LoundryDetails.this)) {
            apiServiceProvider.getRequestLoundryMenu(loundryId, typeTwo, typeOne, LoundryDetails.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void household(View view) {
        if (serviceItems != null) {
            serviceItems.clear();
            mRecyclerView.setVisibility(View.GONE);
        }

        tvMen.setBackgroundResource(R.color.colorPrimaryDark);
        tvWomen.setBackgroundResource(R.color.colorPrimaryDark);
        tvChild.setBackgroundResource(R.color.colorPrimaryDark);
        tvHousehold.setBackgroundResource(R.color.grey);
        tvHousehold.setTextColor(Color.parseColor("#FFFFFF"));
        tvWomen.setTextColor(Color.parseColor("#303030"));
        tvChild.setTextColor(Color.parseColor("#303030"));
        tvMen.setTextColor(Color.parseColor("#303030"));

        String typeTwo = "4";

        if (NetworkUtils.isNetworkConnected(LoundryDetails.this)) {

            apiServiceProvider.getRequestLoundryMenu(loundryId, typeTwo, typeOne, LoundryDetails.this);

        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }
}
