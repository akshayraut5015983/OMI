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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.RattingListAdapter;
import com.omi.app.adapter.RestureneListAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CommentResponce;
import com.omi.app.models.LoginItems;
import com.omi.app.models.RatingListResponce;
import com.omi.app.models.RattingList;
import com.omi.app.models.ResturenListItem;
import com.omi.app.models.ResturentListResponce;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class RatingActivtiy extends AppCompatActivity implements RetrofitListener, AdapterView.OnItemSelectedListener {
    private ApiServiceProvider apiServiceProvider;
    private String restId = "";
    private RatingBar ratingBar;
    Float ratingVal;
    Float ratingvalue;
    private Dialog dialog;
    RecyclerView recyclerView;
    String date, strCmt = "";
    LoginItems loginItems1;
    List<RattingList> rattingLists;
    Spinner spinner;
    private String[] comments = {"Select Your Comment", "Awesome... Want to test Again.", "Food quality is good", "Taste my best", "Quantity is very less", "Need improvement", "Taste is not good"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            restId = bundle.getString("rest_id");
        }
        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        spinner = findViewById(R.id.size_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(RatingActivtiy.this, android.R.layout.simple_spinner_item, comments);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setRating(Float.valueOf(String.valueOf(1.5)));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingVal = (Float) rating;
                ratingvalue = (Float) ratingBar.getRating();
            }
        });
        loginItems1 = (LoginItems) SharePreferenceUtility.getPreferences(RatingActivtiy.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (NetworkUtils.isNetworkConnected(RatingActivtiy.this)) {
            dialog.show();
            apiServiceProvider.getrequestRatingList(restId, RatingActivtiy.this);
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
        if (responseBody instanceof RatingListResponce) {
            RatingListResponce ratingListResponce = (RatingListResponce) responseBody;
            rattingLists = ((RatingListResponce) responseBody).data;

            if (ratingListResponce.flag) {
                RattingListAdapter rattingListAdapter = new RattingListAdapter(rattingLists, RatingActivtiy.this);
                recyclerView.setAdapter(rattingListAdapter);
            }
        } else if (responseBody instanceof CommentResponce) {
            CommentResponce commentResponce = (CommentResponce) responseBody;
            if (commentResponce.flag) {
                if (rattingLists != null) {
                    rattingLists.clear();
                }
                apiServiceProvider.getrequestRatingList(restId, RatingActivtiy.this);
                Toast.makeText(this, "Your comment added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No comment available");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
    }

    public void submit(View view) {
        if (strCmt.equals("Select Your Comment")) {
            Toast.makeText(this, "Select Your Comment", Toast.LENGTH_SHORT).show();
        } else {
            if (NetworkUtils.isNetworkConnected(RatingActivtiy.this)) {
                dialog.show();
                apiServiceProvider.getrequestAddComment(String.valueOf(ratingVal), "Restaurant", restId, date, loginItems1.firstName + " " + loginItems1.lastName, strCmt, RatingActivtiy.this);
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
            }
//        Toast.makeText(getApplicationContext(), " Ratings : " + String.valueOf(ratingVal) + "" + " Ratings1 : " + ratingvalue + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strCmt = comments[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
