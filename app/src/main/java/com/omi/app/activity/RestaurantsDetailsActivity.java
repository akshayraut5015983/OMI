package com.omi.app.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.ResturentDetailsResponce;
import com.omi.app.utils.Constants;
import com.squareup.picasso.Picasso;

public class RestaurantsDetailsActivity extends AppCompatActivity implements RetrofitListener {
    TextView tvName, tvAdr, tvMob, tvHr, tvOther, tvClose, tvDisp;
    private ApiServiceProvider apiServiceProvider;
    RoundedImageView imageView;
    String restId = "";
    String name, adr, mob, hr, close, img, other, disp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resturent_details_activty);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        tvName = findViewById(R.id.tvname);
        tvAdr = findViewById(R.id.tvadr);
        imageView = findViewById(R.id.img);
        tvMob = findViewById(R.id.tvmob);
        tvHr = findViewById(R.id.tvhr);
        tvOther = findViewById(R.id.tvother);
        tvClose = findViewById(R.id.tvclose);
        tvDisp = findViewById(R.id.tvdisp);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            restId = extra.getString("rest_id");
            name = extra.getString("name");
            adr = extra.getString("adr");
            mob = extra.getString("mob");
            disp = extra.getString("dis");
            hr = extra.getString("hr");
            close = extra.getString("clo");
            other = extra.getString("ot");
            img = extra.getString("img");

        }

        tvName.setText(name);
        tvMob.setText(mob);
        tvAdr.setText(adr);
        tvHr.setText(hr);
        tvDisp.setText(disp);
        tvOther.setText(other);
        tvClose.setText(close);
        setTitle(name);
        String img_url = Constants.AppConst.RESTAURANT_IMAGE_BASE_URL + img;
        Glide.with(this).load(img_url).into(imageView);

    }

    public void backimage(View view) {
        finish();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof ResturentDetailsResponce) {
            ResturentDetailsResponce resturentDetailsResponce = (ResturentDetailsResponce) responseBody;
            if (resturentDetailsResponce.flag) {
                String name, adr, mob, hr, close, img, other;
                name = resturentDetailsResponce.data.name;
                adr = resturentDetailsResponce.data.address;
                mob = resturentDetailsResponce.data.mobile;
                hr = resturentDetailsResponce.data.hrOfOperation;
                other = resturentDetailsResponce.data.otherFacility;
                close = resturentDetailsResponce.data.closeDay;
                tvName.setText(name);
                tvMob.setText(mob);
                tvAdr.setText(adr);
                tvHr.setText(hr);
                tvOther.setText(other);
                tvClose.setText(close);
                Picasso.with(this).load(Constants.AppConst.HomePathMain + resturentDetailsResponce.data.photo).into(imageView);
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(this, errorObject.getDeveloperMessage(), Toast.LENGTH_SHORT).show();
    }

    public void coll(View view) {
        String phoneNumber = tvMob.getText().toString();
        if (!phoneNumber.isEmpty()) {
            if (checkPermission(Manifest.permission.CALL_PHONE)) {
                String dial = "tel:" + phoneNumber;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(RestaurantsDetailsActivity.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Mobile Filed Empty", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String callPhone) {
        return ContextCompat.checkSelfPermission(RestaurantsDetailsActivity.this, callPhone) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {"android.permission.CALL_PHONE"};
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    public void onMenu(View view) {
        Intent i = new Intent(RestaurantsDetailsActivity.this, RestaurantsActivity.class);
        i.putExtra("rest_id", restId);
        i.putExtra("rest_name", name);
        i.putExtra("rest_adr", adr);
        i.putExtra("img", img);
        startActivity(i);
    }
}
