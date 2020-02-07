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
import com.omi.app.R;
import com.omi.app.utils.Constants;
import com.squareup.picasso.Picasso;

public class HomeActivityDetils extends AppCompatActivity {
    String name, mobile, address, hrOperation, Discription, other, img;
    TextView tvName, tvAdr, tvMob, tvHrOperation, tvDisp, tvOther;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_home_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tvName = findViewById(R.id.tv_name);
        tvAdr = findViewById(R.id.tv_adr);
        imageView = findViewById(R.id.photo);
        tvMob = findViewById(R.id.tv_mob);
        tvHrOperation = findViewById(R.id.tv_hr);
        tvDisp = findViewById(R.id.tv_disp);
        tvOther = findViewById(R.id.tv_other);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            name = extra.getString("name");
            address = extra.getString("adr");
            mobile = extra.getString("mob");
            hrOperation = extra.getString("hr");
            Discription = extra.getString("disp");
            other = extra.getString("other");
            img = extra.getString("img");
        }
        tvName.setText(name);
        tvMob.setText(mobile);
        tvAdr.setText(address);
        tvHrOperation.setText(hrOperation);
        tvDisp.setText(Discription);
        tvOther.setText(other);

        String img_url = Constants.AppConst.HomeService +""+ img;
        Glide.with(this).load(img_url).into(imageView);

        askPermissions();
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
                Toast.makeText(HomeActivityDetils.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
            }
            /*Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);*/
        } else {
            Toast.makeText(this, "Mobile Filed Empty", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String callPhone) {
        return ContextCompat.checkSelfPermission(HomeActivityDetils.this, callPhone) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {

                "android.permission.CALL_PHONE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    public void backimage(View view) {
        finish();
    }
}
