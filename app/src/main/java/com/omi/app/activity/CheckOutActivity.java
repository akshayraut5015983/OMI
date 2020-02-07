package com.omi.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartItemLoundry;
import com.omi.app.models.CartListLoundryResponce;
import com.omi.app.models.CartListResponce;
import com.omi.app.models.LoginItems;
import com.omi.app.models.OrderPlaceLoundryResponce;
import com.omi.app.models.OrderPlaceResponce;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;
import static com.omi.app.utils.Constants.AppConst.USER_LOCATION;


public class CheckOutActivity extends AppCompatActivity implements RetrofitListener, PaymentResultListener {

    EditText mEtFlat, mEtStreet, mEtLandmark;
    Button btnLogin;
    RadioButton mRbOnline, mRbOffline;
    private ApiServiceProvider apiServiceProvider;
    private LoginItems mLoginItems;
    private String razorpayPaymentID;
    private String payment_status;
    private Context mContext;
    private int wallet = 0;
    private boolean IS_ONLINE;
    private String flat_number, street, landmark;
    private CircularProgressView circularProgressView;
    private TextView tv_holdon, tv_success, tv_location;
    private ImageView iv_success;
    private RelativeLayout mRlCircular;
    private String res_id;
    private boolean IS_FROM_LAUNDRY = false;
    private Dialog dialog;
    private boolean IS_WALLET_USED = false;
    private String address, payable_amount;
    private int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getSupportActionBar().hide();

        if (getIntent() != null) {
            res_id = getIntent().getStringExtra("res_id");
            IS_FROM_LAUNDRY = getIntent().getBooleanExtra("is_from_laundry", false);
            IS_WALLET_USED = getIntent().getBooleanExtra("is_wallet_used", false);
            payable_amount = getIntent().getStringExtra("payable_amount");

            String tmp_data[] = payable_amount.split(" ");
            String tmp_amount[] = tmp_data[1].split("\\.");
            if (Integer.parseInt(tmp_amount[1]) > 0) {
                amount = Integer.parseInt(tmp_amount[0]) + 1;
            } else {
                amount = Integer.parseInt(tmp_amount[0]);
            }

        }

        (findViewById(R.id.btn_reg_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mEtFlat = findViewById(R.id.etFlatNo);
        mEtStreet = findViewById(R.id.etStreetNo);
        mEtLandmark = findViewById(R.id.etLandmark);
        mRbOnline = findViewById(R.id.rbOnlinePayment);
        mRbOffline = findViewById(R.id.rbcash);
        tv_holdon = findViewById(R.id.txt_holdon);
        tv_location = findViewById(R.id.tv_location);
        tv_success = findViewById(R.id.txt_success);
        circularProgressView = findViewById(R.id.circular_progressbar);
        mRlCircular = findViewById(R.id.rl_circular_image);
        iv_success = findViewById(R.id.img_complete);
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(CheckOutActivity.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        mContext = CheckOutActivity.this;
        btnLogin = findViewById(R.id.btnconfirm);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
            }
        });

        if (IS_WALLET_USED) {
            mRbOffline.setVisibility(View.GONE);
            ((ImageView) findViewById(R.id.imgcash)).setVisibility(View.GONE);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        if (NetworkUtils.isNetworkConnected(mContext)) {
            if (IS_FROM_LAUNDRY) {
                apiServiceProvider.getRequestCartListLoundry(mLoginItems.id, CheckOutActivity.this);
            } else {
                apiServiceProvider.getRequestCartList(mLoginItems.id, CheckOutActivity.this);
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
        address = (String) SharePreferenceUtility.getPreferences(mContext, USER_LOCATION, SharePreferenceUtility.PREFTYPE_STRING);

//        address = (String) SharePreferenceUtility.getPreferences(mContext, CITY, SharePreferenceUtility.PREFTYPE_STRING);
        tv_location.setText(address);

        mRbOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mRbOnline.isChecked()) {
                    mRbOnline.setChecked(false);
                }
                mRbOffline.setChecked(isChecked);
            }
        });

        mRbOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mRbOffline.isChecked()) {
                    mRbOffline.setChecked(false);
                }
                mRbOnline.setChecked(isChecked);
            }
        });
    }

    private void validateEntries() {
        boolean checkEntries = true;

        flat_number = mEtFlat.getText().toString();
        street = mEtStreet.getText().toString();
        landmark = mEtLandmark.getText().toString();

        if (TextUtils.isEmpty(flat_number)) {
            Toast.makeText(this, "Please enter flat number", Toast.LENGTH_SHORT).show();
            checkEntries = false;
            return;
        }

        if (TextUtils.isEmpty(street)) {
            Toast.makeText(this, "Please enter street number", Toast.LENGTH_SHORT).show();
            checkEntries = false;
            return;
        }

        if (TextUtils.isEmpty(landmark)) {
            Toast.makeText(this, "Please enter landmark", Toast.LENGTH_SHORT).show();
            checkEntries = false;
            return;
        }

        if (!mRbOnline.isChecked() && !mRbOffline.isChecked()) {
            Toast.makeText(this, "Please check payment mode.", Toast.LENGTH_SHORT).show();
            checkEntries = false;
            return;
        }

        if (checkEntries) {
            if (NetworkUtils.isNetworkConnected(CheckOutActivity.this)) {
                if (mRbOnline.isChecked()) {
                    startPayment();
                } else {
                    dialog.show();
                    if (IS_FROM_LAUNDRY) {
                        apiServiceProvider.getRequestOrderPlaceLoundry(mLoginItems.id, res_id, mLoginItems.firstName + " " + mLoginItems.lastName, mLoginItems.mobileNo, flat_number + ", " + street + ", " + landmark + " " + address, "1", "0", IS_WALLET_USED, this);
                    } else {
                        apiServiceProvider.getRequestOrderPlace(mLoginItems.id, res_id, mLoginItems.firstName + " " + mLoginItems.lastName, mLoginItems.mobileNo, flat_number + ", " + street + ", " + landmark + " " + address, "1", "0", IS_WALLET_USED, String.valueOf(amount), this);
                    }
                }
            } else {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof OrderPlaceResponce) {
            OrderPlaceResponce orderPlaceResponce = (OrderPlaceResponce) responseBody;
            if (orderPlaceResponce.flag) {
                mRlCircular.setVisibility(View.VISIBLE);
                showLoader();
                Toast.makeText(this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, orderPlaceResponce.data, Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof CartListLoundryResponce) {
            CartListLoundryResponce cartListLoundryResponce = (CartListLoundryResponce) responseBody;
            List<CartItemLoundry> arrItemCartDetails = cartListLoundryResponce.data.itemInfo;

            for (int i = 0; i < arrItemCartDetails.size(); i++) {
                wallet = Integer.parseInt(arrItemCartDetails.get(i).itemTotal) + wallet;
            }

        } else if (responseBody instanceof CartListResponce) {
            CartListResponce cartListResponce = (CartListResponce) responseBody;

            for (int i = 0; i < cartListResponce.itemData.size(); i++) {
                wallet = Integer.parseInt(cartListResponce.itemData.get(i).itemTotal) + wallet;
            }

        } else if (responseBody instanceof OrderPlaceLoundryResponce) {
            OrderPlaceLoundryResponce orderPlaceResponce = (OrderPlaceLoundryResponce) responseBody;
            if (orderPlaceResponce.flag) {
                mRlCircular.setVisibility(View.VISIBLE);
                showLoader();
                Toast.makeText(this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, orderPlaceResponce.data, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(com.omi.app.utils.ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    private void showLoader() {
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.blink_amination);
        iv_success.setVisibility(View.VISIBLE);
        iv_success.setAnimation(mAnimation);
        circularProgressView.setVisibility(View.INVISIBLE);
        tv_holdon.setVisibility(View.INVISIBLE);
        tv_success.setVisibility(View.VISIBLE);
        circularProgressView.stopAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CheckOutActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }

    private void startPayment() {

        // You need to pass current activity in order to let Razorpay create CheckoutActivity

        Log.d("wallet_use", String.valueOf(wallet));
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setImage(R.drawable.omi_logo);
        try {
            //final_total=final_total+100;
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            //    options.put("amount", final_total*100);
            options.put("amount", amount * 100);
            JSONObject preFill = new JSONObject();
            preFill.put("email", mLoginItems.email);
            preFill.put("contact", mLoginItems.mobileNo);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        this.razorpayPaymentID = razorpayPaymentID;
        if (IS_FROM_LAUNDRY) {
            apiServiceProvider.getRequestOrderPlaceLoundry(mLoginItems.id, res_id, mLoginItems.firstName + " " + mLoginItems.lastName, mLoginItems.mobileNo, flat_number + ", " + street + ", " + landmark + " " + address, "2", razorpayPaymentID, IS_WALLET_USED, this);
        } else {
            apiServiceProvider.getRequestOrderPlace(mLoginItems.id, res_id, mLoginItems.firstName + " " + mLoginItems.lastName, mLoginItems.mobileNo, flat_number + ", " + street + ", " + landmark + " " + address, "2", razorpayPaymentID, IS_WALLET_USED, String.valueOf(amount), this);
        }
    }

    /* *
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     * */

    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            payment_status = "Failure";
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("CheckOutActivity", "Exception in onPaymentError", e);
        }
    }
}
