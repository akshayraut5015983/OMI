package com.omi.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.OtpVerificationResponse;
import com.omi.app.models.RegisterResponse;
import com.omi.app.models.SendOtpVerificationResponse;
import com.omi.app.models.VerifyDeviceResponce;
import com.omi.app.models.VerifyEmailResponce;
import com.omi.app.models.VerifyMobileResponce;
import com.omi.app.utils.CommonUtils;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;

public class SignUpActivity extends AppCompatActivity implements RetrofitListener {
    EditText mEtFirstName, mEtLastName, mEtEmail, mEtPassword, mEtMobile, mEtAdr;
    private ApiServiceProvider apiServiceProvider;
    RadioGroup rGrop;
    RadioButton m, f;
    String gender = "";
    String deviceId = "", mob;
    String fName, lName, email, pass, address;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private TelephonyManager mTelephonyManager;
    private Dialog dialog;
    String otp = "";
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        m = findViewById(R.id.m);
        f = findViewById(R.id.fm);
        rGrop = findViewById(R.id.rGropu);
        /* CheckBox cb = (CheckBox) findViewById(R.id.cbterms);
        Spannable wordtoSpan = new SpannableString("I Agree To the Terms Of Service and Privacy Policy");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 15, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new UnderlineSpan(), 15, 31, 0);
        cb.setText(wordtoSpan);*/
        mEtFirstName = findViewById(R.id.editFirstName);
        mEtLastName = findViewById(R.id.editLastName);
        mEtMobile = findViewById(R.id.editMobile);
        mEtAdr = findViewById(R.id.editAdr);
        mEtEmail = findViewById(R.id.editEmail);
        mEtPassword = findViewById(R.id.editPassword);
        apiServiceProvider = ApiServiceProvider.getInstance(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);
        checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, TermesAndCondiActivity.class));
            }
        });
        checkBox.setText(Html.fromHtml("By Logging in you agree to OMI <u> tearm's of service, privacy policy</u>"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = mTelephonyManager.getDeviceId();
                Log.d("msg", "Device Imei " + deviceId);
            }
        } else {
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = mTelephonyManager.getDeviceId();
            Log.d("msg", "Device Imei " + deviceId);
        }

        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(SignUpActivity.this, OtpVerificationActivity.class);
                startActivity(i);*/

                if (checkBox.isChecked()) {
                    validate();

                } else {
                    Toast.makeText(SignUpActivity.this, "Please check terme's and condition", Toast.LENGTH_SHORT).show();
                }
//
            }
        });
    }

    private void validate() {

        fName = mEtFirstName.getText().toString();
        lName = mEtLastName.getText().toString();
        mob = mEtMobile.getText().toString();
        email = mEtEmail.getText().toString();
        address = mEtAdr.getText().toString();
        pass = mEtPassword.getText().toString();
        gender = ((RadioButton) findViewById(rGrop.getCheckedRadioButtonId())).getText().toString();

        boolean validate_entries = true;

        if (TextUtils.isEmpty(fName)) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter first name", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lName)) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter last name", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mob)) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter mobile number", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (mob.length() > 10 || mob.length() < 10) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter correct mobile number", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter email id", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!CommonUtils.isEmailValid(email)) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter correct email id", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter address", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            validate_entries = false;
            Snackbar.make(findViewById(android.R.id.content), "Please enter password", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(deviceId)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                            PERMISSIONS_REQUEST_READ_PHONE_STATE);
                } else {
                    mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    deviceId = mTelephonyManager.getDeviceId();
                    Log.d("msg", "Device Imei " + deviceId);
                }
            } else {
                mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = mTelephonyManager.getDeviceId();
                Log.d("msg", "Device Imei " + deviceId);
            }
        }

        if (validate_entries) {
            if (NetworkUtils.isNetworkConnected(SignUpActivity.this)) {
                dialog.show();
                apiServiceProvider.getRequestDeviceVerify(deviceId, this);
            } else {
                Toast.makeText(this, "Please check your internet connection!.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof VerifyDeviceResponce) {
            VerifyDeviceResponce verifyDeviceResponce = (VerifyDeviceResponce) responseBody;
            if (!verifyDeviceResponce.flag) {
                dialog.dismiss();
                Toast.makeText(this, verifyDeviceResponce.msg, Toast.LENGTH_SHORT).show();
            } else {
                apiServiceProvider.getRequestEmailVerify(email, SignUpActivity.this);
            }
        }
        if (responseBody instanceof VerifyEmailResponce) {
            VerifyEmailResponce em = (VerifyEmailResponce) responseBody;
            if (!em.flag) {
                dialog.dismiss();
                Toast.makeText(this, em.msg, Toast.LENGTH_SHORT).show();
            } else {
                apiServiceProvider.getRequestMobileVerify(mob, SignUpActivity.this);
            }
        }
        if (responseBody instanceof VerifyMobileResponce) {
            VerifyMobileResponce verifyMobileResponce = (VerifyMobileResponce) responseBody;
            if (!verifyMobileResponce.flag) {
                dialog.dismiss();
                Toast.makeText(this, verifyMobileResponce.msg, Toast.LENGTH_SHORT).show();
            } else {
                apiServiceProvider.getRequestSendOTP(mob, "1", SignUpActivity.this);
            }
        }

        if (responseBody instanceof SendOtpVerificationResponse) {
            SendOtpVerificationResponse otpVerificationResponse = (SendOtpVerificationResponse) responseBody;
            if (otpVerificationResponse.flag) {
                openOtpScreenDialog();
            }
        }

        if (responseBody instanceof RegisterResponse) {
            RegisterResponse loginResponse = (RegisterResponse) responseBody;
            if (loginResponse.flag) {
                dialog.dismiss();
                Toast.makeText(this, "Registration successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Incorrect Something.", Snackbar.LENGTH_SHORT).show();
            }
        }
        if (responseBody instanceof OtpVerificationResponse) {
            dialog.dismiss();
            OtpVerificationResponse otpVerificationResponse = (OtpVerificationResponse) responseBody;
            if (otpVerificationResponse.flag) {
                apiServiceProvider.getRequestRegister(fName, lName, email, mob, address, gender, pass, deviceId, SignUpActivity.this);
                Toast.makeText(this, otpVerificationResponse.msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, otpVerificationResponse.msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openOtpScreenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.otp_dialog, null, false);
        builder.setView(view);

        final Pinview pin = (Pinview) view.findViewById(R.id.pinview);
        pin.setPinBackgroundRes(R.drawable.sample_background);
        Button btn_submit = view.findViewById(R.id.bt_submit);
        final EditText et_passwprd = view.findViewById(R.id.editPassword);
        final Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Make api calls here or what not
                otp = pinview.getValue();
                Toast.makeText(SignUpActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                apiServiceProvider.getRequestVerifyOtp(mob, otp, "1", et_passwprd.getText().toString().trim(), SignUpActivity.this);
            }
        });

        dialog.show();


    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Snackbar.make(findViewById(android.R.id.content), "Invalid Credentials", Snackbar.LENGTH_SHORT).show();
    }

    public void backimage(View view) {
        finish();
    }
}
