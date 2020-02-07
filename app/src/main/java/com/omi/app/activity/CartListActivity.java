package com.omi.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.CartListAdapter;
import com.omi.app.adapter.LaundryCartListAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CardResponce;
import com.omi.app.models.CartItemLoundry;
import com.omi.app.models.CartListLoundryResponce;
import com.omi.app.models.CartListResponce;
import com.omi.app.models.ItemCartDetails;
import com.omi.app.models.LoginItems;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.text.NumberFormat;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class CartListActivity extends AppCompatActivity implements RetrofitListener {

    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRecyclerView;
    private LoginItems loginItems;
    private Context mContext;
    private CartListResponce cartListResponce;
    private boolean IS_FROM_LAUNDRY = false;
    private CartListLoundryResponce cartListLoundryResponce;
    private Dialog dialog;
    private TextView mTvItemPrice, mTvTax, mTvTotalPrice, mTvWalletAmount;
    private LinearLayout mLlWallet, mLlLaundryCat;
    private CheckBox mCbWalletUsed;
    private boolean IS_WALLET_USED = false;
    private MaterialCardView mCvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        mContext = CartListActivity.this;
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        mRecyclerView = findViewById(R.id.rvmenu);
        mLlLaundryCat = findViewById(R.id.id_laundry_cat);
        mTvItemPrice = findViewById(R.id.tv_total_item_cost);
        mCbWalletUsed = findViewById(R.id.cb_wallet_used);
        mTvTax = findViewById(R.id.tv_tax_services);
        mTvTotalPrice = findViewById(R.id.tv_total_cost);
        mLlWallet = findViewById(R.id.ll_wallet);
        mTvWalletAmount = findViewById(R.id.tv_wallet_amount);
        mCvPrice = findViewById(R.id.mcv_price);

        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView btnSubmitProfile = findViewById(R.id.btn_reg_back);

        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null) {
            IS_FROM_LAUNDRY = getIntent().getBooleanExtra("from_laundry", false);
        }

        if (IS_FROM_LAUNDRY) {
            mLlLaundryCat.setVisibility(View.VISIBLE);
        } else {
            mLlLaundryCat.setVisibility(View.GONE);
        }


        Button btnLogin = findViewById(R.id.btn_add_address);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_FROM_LAUNDRY) {
                    Intent i = new Intent(CartListActivity.this, CheckOutActivity.class);
                    i.putExtra("res_id", cartListLoundryResponce.data.dhobiInfo.id);
                    i.putExtra("is_from_laundry", IS_FROM_LAUNDRY);
                    i.putExtra("is_wallet_used", IS_WALLET_USED);
                    i.putExtra("payable_amount", mTvTotalPrice.getText().toString());
                    startActivity(i);
                } else {
                    Intent i = new Intent(CartListActivity.this, CheckOutActivity.class);
                    i.putExtra("res_id", cartListResponce.restInfo.id);
                    i.putExtra("is_from_laundry", IS_FROM_LAUNDRY);
                    i.putExtra("is_wallet_used", IS_WALLET_USED);
                    i.putExtra("payable_amount", mTvTotalPrice.getText().toString());
                    startActivity(i);
                }
            }
        });

        mCbWalletUsed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                IS_WALLET_USED = isChecked;
                if (IS_FROM_LAUNDRY) {
                    double total_price = cartListLoundryResponce.priceDetails.finalAmount;
                    double wallet_amt = cartListLoundryResponce.priceDetails.walletUsableAmount;
                    if (isChecked) {
                        mTvTotalPrice.setText("\u20B9 " + String.valueOf(total_price - wallet_amt));
                    } else {
                        mTvTotalPrice.setText("\u20B9 " + String.valueOf(total_price));
                    }
                } else {
                    double total_price = cartListResponce.priceDetails.finalAmount;
                    double wallet_amt = cartListResponce.priceDetails.walletUsableAmount;
                    if (isChecked) {
                        mTvTotalPrice.setText("\u20B9 " +  String.valueOf(total_price - wallet_amt));
                    } else {
                        mTvTotalPrice.setText("\u20B9 " +  String.valueOf(total_price));
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.isNetworkConnected(mContext)) {
            dialog.show();
            if (IS_FROM_LAUNDRY) {
                apiServiceProvider.getRequestCartListLoundry(loginItems.id, CartListActivity.this);
            } else {
                apiServiceProvider.getRequestCartList(loginItems.id, CartListActivity.this);
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        NumberFormat format = NumberFormat.getCurrencyInstance();
        if (responseBody instanceof CartListResponce) {
            cartListResponce = (CartListResponce) responseBody;
            if (cartListResponce != null && cartListResponce.itemData != null) {

                List<ItemCartDetails> arrItemCartDetails = cartListResponce.itemData;
                if (arrItemCartDetails.size() > 0) {
                    mCvPrice.setVisibility(View.VISIBLE);
                    CartListAdapter cartListAdapter = new CartListAdapter(arrItemCartDetails, CartListActivity.this, cartListResponce.restInfo.id, CartListActivity.this);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mRecyclerView.setAdapter(cartListAdapter);


                    mTvItemPrice.setText("\u20B9 " + String.valueOf(cartListResponce.priceDetails.totalAmount));
                    mTvTax.setText("\u20B9 " + String.valueOf(cartListResponce.priceDetails.gstCharges + cartListResponce.priceDetails.deliveryCharges));
                    mTvTotalPrice.setText("\u20B9 " + String.valueOf(cartListResponce.priceDetails.finalAmount));

                    if (cartListResponce.priceDetails.walletUsable) {

                        if (cartListResponce.priceDetails.walletUsableAmount > 0) {
                            mLlWallet.setVisibility(View.VISIBLE);
                            mTvWalletAmount.setText(String.valueOf(cartListResponce.priceDetails.walletUsableAmount));
                        } else {
                            mLlWallet.setVisibility(View.GONE);
                        }
                    } else {
                        mLlWallet.setVisibility(View.GONE);
                    }
                }
            } else {
                mCvPrice.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No Items in cart. Shop Now");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            }

        } else if (responseBody instanceof CartListLoundryResponce) {
            cartListLoundryResponce = (CartListLoundryResponce) responseBody;
            List<CartItemLoundry> arrItemCartDetails = cartListLoundryResponce.data.itemInfo;
            mCvPrice.setVisibility(View.VISIBLE);
//            CartListAdapter cartListAdapter = new CartListAdapter(arrItemCartDetails, CartListActivity.this, CartListActivity.this, cartListLoundryResponce.data.dhobiInfo.id, IS_FROM_LAUNDRY);
//            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            LaundryCartListAdapter laundryCartListAdapter = new LaundryCartListAdapter(arrItemCartDetails, mContext, cartListLoundryResponce.data.dhobiInfo.id);
            mRecyclerView.setAdapter(laundryCartListAdapter);

            mTvItemPrice.setText("\u20B9 " + String.valueOf(cartListLoundryResponce.priceDetails.totalAmount));
            mTvTax.setText("\u20B9 " + String.valueOf(cartListLoundryResponce.priceDetails.gstCharges + cartListLoundryResponce.priceDetails.deliveryCharges));
            mTvTotalPrice.setText("\u20B9 " + String.valueOf(cartListLoundryResponce.priceDetails.finalAmount));

            if (cartListLoundryResponce.priceDetails.walletUsable) {
                if (cartListLoundryResponce.priceDetails.walletUsableAmount > 0) {
                    mTvWalletAmount.setText(String.valueOf(cartListLoundryResponce.priceDetails.walletUsableAmount));
                    mLlWallet.setVisibility(View.VISIBLE);
                } else {
                    mLlWallet.setVisibility(View.GONE);
                }

            } else {
                mLlWallet.setVisibility(View.GONE);
            }

        } else if (responseBody instanceof CardResponce) {
            CardResponce cardResponce = (CardResponce) responseBody;
            if (cardResponce.flag) {
                Toast.makeText(mContext, cardResponce.data, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        if (throwable.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 23 path $.data")) {
            mCvPrice.setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Items in cart. Shop Now");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();
        } else {
            Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
