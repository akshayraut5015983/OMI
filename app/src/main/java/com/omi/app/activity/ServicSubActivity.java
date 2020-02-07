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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.SubServiceAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.SubServiceItem;
import com.omi.app.models.SubServiceResponce;
import com.omi.app.utils.NetworkUtils;

import java.util.List;

public class ServicSubActivity extends AppCompatActivity implements RetrofitListener {
    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRecyclerView;
    String id = "", name = "";
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_subservice);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        mRecyclerView = findViewById(R.id.rcylersubservice);
        TextView tvName = findViewById(R.id.tv_name);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id = extras.getString("key");
            name = extras.getString("name");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        tvName.setText(name);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        if (NetworkUtils.isNetworkConnected(ServicSubActivity.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            dialog.show();
            apiServiceProvider.getRequestSubService(id, ServicSubActivity.this);
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
        if (responseBody instanceof SubServiceResponce) {
            SubServiceResponce serviceResponce = (SubServiceResponce) responseBody;
            if (serviceResponce.flag) {
                List<SubServiceItem> serviceItems = ((SubServiceResponce) responseBody).data;
                SubServiceAdapter listAdapter = new SubServiceAdapter(serviceItems, ServicSubActivity.this);
                mRecyclerView.setAdapter(listAdapter);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No Data Available");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            }
        }

    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Data Available");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }
}
