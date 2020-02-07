package com.omi.app.activity;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CityItem;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.LoginResponse;
import com.omi.app.utils.CommonUtils;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import static com.omi.app.utils.Constants.AppConst.CITY_LOCATION;
import static com.omi.app.utils.Constants.AppConst.IS_LOGIN;
import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class LoginActivity extends AppCompatActivity implements RetrofitListener {
    EditText ed_email, ed_pass;
    private ApiServiceProvider apiServiceProvider;
    private Dialog dialog;
    ImageView imageView, imageViewb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSkip = findViewById(R.id.btnSkip);
        TextView tvSignup = findViewById(R.id.tvSignup);
        ed_email = findViewById(R.id.edit_email);
        apiServiceProvider = ApiServiceProvider.getInstance(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        ed_pass = findViewById(R.id.edit_password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, DeliverLocationActivity.class);
                startActivity(i);
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);

            }
        });
        imageView = findViewById(R.id.imga);
        imageViewb = findViewById(R.id.imgb);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(9000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = imageView.getWidth();
                final float translationX = width * progress;
                imageView.setTranslationX(translationX);
                imageViewb.setTranslationX(translationX - width);
            }
        });
        animator.start();

    }

    private void validateData() {
        String email, password;
        email = ed_email.getText().toString().trim();
        password = ed_pass.getText().toString().trim();
        boolean validate_entries = true;

        if (TextUtils.isEmpty(email)) {
            validate_entries = false;
            Toast.makeText(this, "Please enter your email id or mobile number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!CommonUtils.isEmailValid(email) && email.length() != 10) {
            validate_entries = false;
            Toast.makeText(this, "Please enter correct number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            validate_entries = false;
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (validate_entries) {
            if (NetworkUtils.isNetworkConnected(LoginActivity.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
                dialog.show();
                apiServiceProvider.getRequestLogin(email, password, LoginActivity.this);
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) responseBody;
            if (loginResponse.flag) {
                SharePreferenceUtility.saveBooleanPreferences(LoginActivity.this, IS_LOGIN, true);
                SharePreferenceUtility.saveObjectPreferences(LoginActivity.this, LOGIN_ITEMS, loginResponse.data);
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                CityItem cityItem = (CityItem) SharePreferenceUtility.getPreferences(LoginActivity.this,CITY_LOCATION,SharePreferenceUtility.LOCATION_OBJECT);
                if (cityItem!= null) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(LoginActivity.this, DeliverLocationActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Incorrect email id and password.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        Snackbar.make(findViewById(android.R.id.content), "Invalid username or password", Snackbar.LENGTH_SHORT).show();
    }

    public void forgotPass(View view) {
        Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    public void login(View view) {
        LinearLayout linearLayout = findViewById(R.id.goneliner);
        LinearLayout linearLayout1 = findViewById(R.id.onclikkk);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
        linearLayout.startAnimation(animation);
        linearLayout.setVisibility(View.GONE);
        linearLayout1.setVisibility(View.VISIBLE);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_right);
        linearLayout1.startAnimation(animation1);
    }

    public void signUp(View view) {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }

    public void skipmain(View view) {
        Intent i = new Intent(LoginActivity.this, DeliverLocationActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void termes(View view) {
        Intent i = new Intent(LoginActivity.this, TermesAndCondiActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
