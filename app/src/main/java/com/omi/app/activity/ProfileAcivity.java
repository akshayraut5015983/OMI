package com.omi.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.models.LoginItems;
import com.omi.app.utils.SharePreferenceUtility;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class ProfileAcivity extends AppCompatActivity {
    TextView tvName, tvEmail, tvMob;
    String fName, lName, mobile, email, adress, gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivtiy_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tvName = findViewById(R.id.edit_name);

        tvMob = findViewById(R.id.edit_mob);
        tvEmail = findViewById(R.id.edit_email);

        getSessionData();


    }

    private void getSessionData() {
        LoginItems loginItems1 = (LoginItems) SharePreferenceUtility.getPreferences(ProfileAcivity.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        if (loginItems1 == null) {
            Toast.makeText(ProfileAcivity.this, "Please Login", Toast.LENGTH_SHORT).show();
        } else {
            String uid = loginItems1.firstName + " " + loginItems1.lastName;
            tvName.setText(uid);
            tvMob.setText(loginItems1.mobileNo);
            tvEmail.setText(loginItems1.email);
        }
    }

    public void backimage(View view) {
        finish();
    }
}
