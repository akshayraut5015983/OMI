package com.omi.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omi.app.activity.OrderActivity;
import com.omi.app.adapter.ItemListAdapter;
import com.omi.app.adapter.OrderListAdapter;
import com.omi.app.R;
import com.omi.app.models.ItemInfo;
import com.omi.app.models.ItemListResponce;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.LoginItems;
import com.omi.app.models.OrderListItems;
import com.omi.app.models.OrderResponse;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class RestaurantOrderListFragment extends Fragment implements RetrofitListener {

    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRecyclerView;
    private LoginItems loginItems;
    private Context mContext;
    private Dialog dialog;
    private View mParentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_order_list, container, false);
        mContext = getContext();
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mRecyclerView = mParentView.findViewById(R.id.rv_order_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (loginItems != null) {
            if (NetworkUtils.isNetworkConnected(mContext)) {
                dialog.show();
                apiServiceProvider.getRequestOrderList(loginItems.id, this);
            } else {
                Toast.makeText(mContext, "Please check internet connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "Please login", Toast.LENGTH_SHORT).show();
        }

        return mParentView;
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof OrderResponse) {
            OrderResponse orderResponse = (OrderResponse) responseBody;
            List<OrderListItems> orderListItems = orderResponse.data;
            if (orderListItems.size()>0) {
                OrderListAdapter orderListAdapter = new OrderListAdapter(mContext, orderListItems);
                mRecyclerView.setAdapter(orderListAdapter);
            }else {
                Toast.makeText(mContext, "No restaurant order found", Toast.LENGTH_SHORT).show();
            }
        }
        if (responseBody instanceof ItemListResponce) {

            ItemListResponce orderDetailsResponse = (ItemListResponce) responseBody;
            if (orderDetailsResponse.flag) {
                try {
                    List<ItemInfo> accountDetailsItems = ((ItemListResponce) responseBody).data.itemInfo;
                    ItemListAdapter listAdapter = new ItemListAdapter(accountDetailsItems, mContext);

                    Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.activity_testing);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    dialog.show();
                    RecyclerView rvTest = (RecyclerView) dialog.findViewById(R.id.recycler);
                    rvTest.setHasFixedSize(true);
                    rvTest.setLayoutManager(new LinearLayoutManager(mContext));
                    rvTest.setAdapter(listAdapter);
                } catch (Exception e) {
                    Toast.makeText(mContext, e + "", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(mContext, "UnSuccess", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onResponseError(com.omi.app.utils.ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
//        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Oops...Restaurant order not found");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();

    }

}
