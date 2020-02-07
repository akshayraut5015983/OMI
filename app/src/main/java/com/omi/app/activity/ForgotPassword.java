package com.omi.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.OtpVerificationResponse;
import com.omi.app.models.SendOtpVerificationResponse;
import com.omi.app.utils.ErrorObject;

public class ForgotPassword extends AppCompatActivity implements RetrofitListener {
    private Dialog mDialog;
    private EditText mEtMobileNumber;
    private ApiServiceProvider apiServiceProvider;
    private String otp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apiServiceProvider = ApiServiceProvider.getInstance(this);

        mEtMobileNumber = findViewById(R.id.et_mobile);
        Button btnSubmitProfile = findViewById(R.id.btn_reset_password);

        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile_number = mEtMobileNumber.getText().toString().trim();
                if (TextUtils.isEmpty(mobile_number) || mobile_number.length() != 10) {
                    Toast.makeText(ForgotPassword.this, "Please enter correct mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    mDialog.show();
                    apiServiceProvider.getRequestSendOTP(mobile_number, "2", ForgotPassword.this);
                }
            }
        });

    }

    private void openOtpScreenDialog(final String mobile_number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.otp_dialog, null, false);
        builder.setView(view);

        final Pinview pin = (Pinview) view.findViewById(R.id.pinview);
        pin.setPinBackgroundRes(R.drawable.sample_background);
        Button btn_submit = view.findViewById(R.id.bt_submit);
        final EditText et_passwprd = view.findViewById(R.id.editPassword);
        et_passwprd.setVisibility(View.VISIBLE);
        final Dialog dialog = builder.create();
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Make api calls here or what not
                otp = pinview.getValue();
                Toast.makeText(ForgotPassword.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_passwprd.getText().toString().trim())) {
                    Toast.makeText(ForgotPassword.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    apiServiceProvider.getRequestVerifyOtp(mobile_number, otp, "2", et_passwprd.getText().toString().trim(), ForgotPassword.this);
                }
            }
        });

        dialog.show();


    }

    public void backimage(View view) {
        finish();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        mDialog.dismiss();
        if (responseBody instanceof SendOtpVerificationResponse) {
            SendOtpVerificationResponse otpVerificationResponse = (SendOtpVerificationResponse) responseBody;
            if (otpVerificationResponse.flag) {
                openOtpScreenDialog(mEtMobileNumber.getText().toString().trim());
            }
        }

        if (responseBody instanceof OtpVerificationResponse) {
            OtpVerificationResponse otpVerificationResponse = (OtpVerificationResponse) responseBody;
            if (otpVerificationResponse.flag) {
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, otpVerificationResponse.msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, otpVerificationResponse.msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        mDialog.dismiss();
        Toast.makeText(this, "Number not available", Toast.LENGTH_SHORT).show();
    }
}
