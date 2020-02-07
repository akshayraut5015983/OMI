package com.omi.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.adapter.WalletAdapter;
import com.omi.app.R;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.LoginItems;
import com.omi.app.models.WalletItems;
import com.omi.app.models.WalletResponse;
import com.omi.app.utils.ErrorObject;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class WalletActivity extends AppCompatActivity implements RetrofitListener {
    boolean isConnected;
    BottomNavigationView btnv;
    TextView txt_walletAmount;
    RecyclerView rv_getwalletList;
    RecyclerView.LayoutManager layoutManager;
    private ApiServiceProvider apiServiceProvider;
    private Dialog dialog;
    private Context mContext;
    private LoginItems loginItems;
    private List<WalletItems> arrWalletItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        mContext = WalletActivity.this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageView back = (ImageView) findViewById(R.id.btn_reg_back);
        txt_walletAmount = findViewById(R.id.tv_wallet_amount);
        rv_getwalletList = findViewById(R.id.rv_getwalletList);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_getwalletList.setLayoutManager(layoutManager);
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.isNetworkConnected(mContext)) {
            dialog.show();
            apiServiceProvider.getRequestWalletList(loginItems.id, WalletActivity.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof WalletResponse) {
            WalletResponse walletResponse = (WalletResponse) responseBody;
            if (walletResponse != null) {
                arrWalletItems = walletResponse.walletHistory;

                if (arrWalletItems.size() > 0) {
                    txt_walletAmount.setText(arrWalletItems.get(0).walletCurrAmt);
                    WalletAdapter walletAdapter = new WalletAdapter(mContext, arrWalletItems);
                    rv_getwalletList.setAdapter(walletAdapter);
                } else {
                    txt_walletAmount.setText("\u20B9" + "0");
                }
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
