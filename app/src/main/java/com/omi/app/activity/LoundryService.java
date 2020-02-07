package com.omi.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.BestSellerAdapter;
import com.omi.app.models.BestSellerItem;
import com.omi.app.models.BestSellerResponce;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartListLoundryResponce;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.LoginItems;
import com.omi.app.models.LondryCardResponce;
import com.omi.app.models.LoundryMenuResponce;
import com.omi.app.models.OrderDetailsLoundryResponce;
import com.omi.app.models.OrderHistoryLoundryResponce;
import com.omi.app.models.OrderPlaceLoundryResponce;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;
import static com.omi.app.utils.Constants.AppConst.LONGI;

public class LoundryService extends AppCompatActivity implements RetrofitListener {
    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRecyclerView;
    String LoundryId;
    TextView trend;
    RecyclerView recyclerView;
    private String longi;
    private String lat;
    HorizontalScrollView  horizontalScrollView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loundryservice);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        trend = findViewById(R.id.tredingplace);
        trend.setText(Html.fromHtml("<u>Best Seller</u> "));
        recyclerView = findViewById(R.id.recyclerview4);
        horizontalScrollView = findViewById(R.id.horb);

        LinearLayoutManager horizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontal);
        lat = (String) SharePreferenceUtility.getPreferences(LoundryService.this, LAT, SharePreferenceUtility.PREFTYPE_STRING);
        longi = (String) SharePreferenceUtility.getPreferences(LoundryService.this, LONGI, SharePreferenceUtility.PREFTYPE_STRING);

        mRecyclerView = findViewById(R.id.rcylersubservice);
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            LoundryId = extras.getString("id");

        }

        if (NetworkUtils.isNetworkConnected(LoundryService.this)) {

            apiServiceProvider.getRequestBestSeller(lat, longi, "0", LoundryService.this);

        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void backimage(View view) {
        finish();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof LondryCardResponce) {
            LondryCardResponce londryCardResponce = (LondryCardResponce) responseBody;
            if (londryCardResponce.flag) {
                Toast.makeText(this, "Added in Loundry Card", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Else Part execute", Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof CartListLoundryResponce) {
            CartListLoundryResponce londryCardResponce = (CartListLoundryResponce) responseBody;
            if (londryCardResponce.flag) {
                Toast.makeText(this, "Card List Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Else Part execute", Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof OrderPlaceLoundryResponce) {
            OrderPlaceLoundryResponce londryCardResponce = (OrderPlaceLoundryResponce) responseBody;
            if (londryCardResponce.flag) {
                Toast.makeText(this, "Order Place Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Else Part execute", Toast.LENGTH_SHORT).show();

            }
        } else if (responseBody instanceof OrderDetailsLoundryResponce) {
            OrderDetailsLoundryResponce londryCardResponce = (OrderDetailsLoundryResponce) responseBody;
            if (londryCardResponce.flag) {
                Toast.makeText(this, "Order Details Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Else Part execute", Toast.LENGTH_SHORT).show();

            }
        } else if (responseBody instanceof OrderHistoryLoundryResponce) {
            OrderHistoryLoundryResponce londryCardResponce = (OrderHistoryLoundryResponce) responseBody;
            if (londryCardResponce.flag) {
                Toast.makeText(this, "Order History Details Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Else Part execute", Toast.LENGTH_SHORT).show();

            }
        } else if (responseBody instanceof LoundryMenuResponce) {
            LoundryMenuResponce londryCardResponce = (LoundryMenuResponce) responseBody;
            if (londryCardResponce.flag) {


            } else {
                Toast.makeText(this, "Else Part execute", Toast.LENGTH_SHORT).show();

            }
        }if (responseBody instanceof BestSellerResponce) {
            BestSellerResponce cardResponce = (BestSellerResponce) responseBody;
            if (cardResponce.flag) {
                recyclerView.setVisibility(View.VISIBLE);
                horizontalScrollView.setVisibility(View.GONE);
                List<BestSellerItem> bestSellerItems = ((BestSellerResponce) responseBody).data;

                BestSellerAdapter bestSellerAdapter = new BestSellerAdapter(bestSellerItems, LoundryService.this);
                recyclerView.setAdapter(bestSellerAdapter);
            } else {
                trend.setVisibility(View.GONE);
//                Toast.makeText(this, "Response Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(this, errorObject.getDeveloperMessage(), Toast.LENGTH_SHORT).show();

    }

    public void addCart(View view) {
       /* if (NetworkUtils.isNetworkConnected(LoundryService.this)) {

            apiServiceProvider.getRequestAddCardLoundry("1", "1", "1", "2", "100", LoundryService.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }*/
    }

    public void addCartList(View view) {

        LoginItems loginItems = (LoginItems) SharePreferenceUtility.getPreferences(LoundryService.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
//        if (loginItems == null) {
//            Toast.makeText(LoundryService.this, "Please Login", Toast.LENGTH_SHORT).show();
//        } else {
//            String uid = String.valueOf(loginItems.id);
        String id = "1";
        if (NetworkUtils.isNetworkConnected(LoundryService.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            apiServiceProvider.getRequestCartListLoundry(id, LoundryService.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

//            }
        }

    }

    public void addOrderPlace(View view) {

      /*  String resid = "1", usid = "1", name = "sapna", adr = "test addr", mob = "1212121212", payment = "1";
        if (NetworkUtils.isNetworkConnected(LoundryService.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            apiServiceProvider.getRequestOrderPlaceLoundry(resid, usid, name, mob, adr, payment, LoundryService.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }*/

    }

    public void addOrderDetails(View view) {

//        Toast.makeText(this, "For Order Details", Toast.LENGTH_SHORT).show();
        String ordId = "1";
        if (NetworkUtils.isNetworkConnected(LoundryService.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            apiServiceProvider.getRequestOrderDetailsLoundry(ordId, LoundryService.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }
    }

    public void addOrderHistory(View view) {
        LoginItems loginItems1 = (LoginItems) SharePreferenceUtility.getPreferences(LoundryService.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        if (loginItems1 == null) {
            Toast.makeText(LoundryService.this, "Please Login", Toast.LENGTH_SHORT).show();
        } else {
            String uid = String.valueOf(loginItems1.id);
            if (NetworkUtils.isNetworkConnected(LoundryService.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
                apiServiceProvider.getRequestOrderHistoryLoundry("1", LoundryService.this);
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    public void onWashPerKgClick(View view) {
        Intent i = new Intent(LoundryService.this, LoundryDetails.class);
        i.putExtra("type", "5");
        i.putExtra("l_id", LoundryId);
        i.putExtra("name","Wash Per Kg");
        startActivity(i);

    }

    public void onDryCleanClick(View view) {
        Intent i = new Intent(LoundryService.this, LoundryDetails.class);
        i.putExtra("type", "4");
        i.putExtra("l_id", LoundryId);
        i.putExtra("name","Dry Clean");

        startActivity(i);

    }

    public void onIronWashClick(View view) {
        Intent i = new Intent(LoundryService.this, LoundryDetails.class);
        i.putExtra("type", "3");
        i.putExtra("l_id", LoundryId);
        i.putExtra("name","Iron Wash");
        startActivity(i);

    }

    public void onWashClick(View view) {
        Intent i = new Intent(LoundryService.this, LoundryDetails.class);
        i.putExtra("type", "2");
        i.putExtra("l_id", LoundryId);
        i.putExtra("name","Wash");
        startActivity(i);

    }

    public void onIronClick(View view) {
        Intent i = new Intent(LoundryService.this, LoundryDetails.class);
        i.putExtra("type", "1");
        i.putExtra("l_id", LoundryId);
        i.putExtra("name","Iron");
        startActivity(i);

    }
}
