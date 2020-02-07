package com.omi.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.adapter.NotificationAdapter;
import com.omi.app.db.DBHelper;
import com.omi.app.models.NotificationModel;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.CART_COUNT;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView mRvNotification;
    private Context mContext;
    private TextView mTvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_adapter);
        mContext = NotificationActivity.this;
        getSupportActionBar().hide();
        mRvNotification = findViewById(R.id.rv_notification);
        mTvNoData = findViewById(R.id.tv_no_data);
        ImageView btnSubmitProfile = findViewById(R.id.btn_reg_back);
        SharePreferenceUtility.saveStringPreferences(this, CART_COUNT, "");
        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRvNotification.setLayoutManager(layoutManager);



    }

    @Override
    public void onResume() {
        super.onResume();
        DBHelper dbHelper = new DBHelper(mContext);
        List<NotificationModel> notificationModels = dbHelper.getAllNotes();
        if (notificationModels.size() > 0) {
            mRvNotification.setVisibility(View.VISIBLE);
            mTvNoData.setVisibility(View.GONE);
            NotificationAdapter notificationAdapter = new NotificationAdapter(notificationModels, mContext, dbHelper);
            mRvNotification.setAdapter(notificationAdapter);
        } else {
            mRvNotification.setVisibility(View.GONE);
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }
}
