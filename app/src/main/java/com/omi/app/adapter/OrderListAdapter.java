package com.omi.app.adapter;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.ItemInfo;
import com.omi.app.models.ItemListLoundry;
import com.omi.app.models.ItemListResponce;
import com.omi.app.models.ItemListRest;
import com.omi.app.models.LaundryListItems;
import com.omi.app.models.OrderListItems;
import com.omi.app.utils.ErrorObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> implements RetrofitListener {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<OrderListItems> arrOrderListItems;
    private List<LaundryListItems> arrLaundryListItems;
    private boolean from_laundry;
    ImageView imgRed, imgYellow, imgGreen;
    private ApiServiceProvider apiServiceProvider;

    public OrderListAdapter(Context mContext, List<OrderListItems> arrOrderListItems) {
        this.mContext = mContext;
        this.arrOrderListItems = arrOrderListItems;
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public OrderListAdapter(Context mContext, List<LaundryListItems> arrLaundryListItems, boolean from_laundry) {
        this.mContext = mContext;
        this.arrLaundryListItems = arrLaundryListItems;
        this.from_laundry = from_laundry;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (from_laundry){
            view = mLayoutInflater.inflate(R.layout.itema_orderloundry, viewGroup, false);

        }else {
             view = mLayoutInflater.inflate(R.layout.itema_order, viewGroup, false);
        }
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {

        if (from_laundry) {
            final LaundryListItems laundry_data = arrLaundryListItems.get(i);
            orderListViewHolder.orderId.setText(laundry_data.orderNo);
            orderListViewHolder.otpId.setText(laundry_data.otp);
            orderListViewHolder.ll2.setVisibility(View.GONE);
            orderListViewHolder.tvtypeName.setText("Laundry name :");
            orderListViewHolder.restaurantName.setText(laundry_data.dhobiName);
            orderListViewHolder.amount.setText("\u20B9 " + laundry_data.totalAmount);
            orderListViewHolder.paymentmode.setText(getPaymentMode(Integer.parseInt(laundry_data.paymentMode)));
            orderListViewHolder.orderDate.setText(changeDateFormat(laundry_data.orderDate));

            orderListViewHolder.status.setText(getPaymentStatus(Integer.parseInt(laundry_data.orderStatus)));
            orderListViewHolder.tvItemInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "Still working", Toast.LENGTH_SHORT).show();
                    showAlertItemDialogLoundry(laundry_data.itemData, mContext);
                }

            });

        } else {
            final OrderListItems orderListItems = arrOrderListItems.get(i);

            orderListViewHolder.orderId.setText(orderListItems.orderNo);
            orderListViewHolder.otpId.setText(orderListItems.userOtp);
            orderListViewHolder.restaurantName.setText(orderListItems.restName);
            orderListViewHolder.orderDate.setText(changeDateFormat(orderListItems.orderDate));
            orderListViewHolder.amount.setText("\u20B9 " + orderListItems.payableAmount);
            orderListViewHolder.paymentmode.setText(getPaymentMode(Integer.parseInt(orderListItems.paymentMode)));
            orderListViewHolder.delBoy.setText(orderListItems.delName);
            orderListViewHolder.status.setText(getPaymentStatus(Integer.parseInt(orderListItems.orderStatus)));
            if (orderListItems.orderStatus.equals("0")||orderListItems.orderStatus.equals("1")) {
                orderListViewHolder.ll2.setVisibility(View.GONE);
            }else {
                orderListViewHolder.ll2.setVisibility(View.VISIBLE);
            }
            orderListViewHolder.tvItemInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    apiServiceProvider.getRequestRestOrderItemList(orderListItems.id, OrderListAdapter.this);
                    showAlertItemDialog(orderListItems.itemData, mContext);
                }
            });
            orderListViewHolder.imageViewColl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    apiServiceProvider.getRequestRestOrderItemList(orderListItems.id, OrderListAdapter.this);
                    callMob(orderListItems.delPhone);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (from_laundry) {
            return arrLaundryListItems.size();
        } else {
            return arrOrderListItems.size();
        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof ItemListResponce) {
            ItemListResponce itemListResponce = (ItemListResponce) responseBody;
            if (itemListResponce.data.itemInfo.size() > 0) {
//                showAlertItemDialog(itemListResponce.data.itemInfo, mContext);
            }
        }
    }

    private void callMob(String phoneNumber) {

        if (!phoneNumber.isEmpty()) {
            if (checkPermission(Manifest.permission.CALL_PHONE)) {
                String dial = "tel:" + phoneNumber;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(mContext, "Mobile Filed Not Match", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String callPhone) {
        return ContextCompat.checkSelfPermission(mContext, callPhone) == PackageManager.PERMISSION_GRANTED;
    }

    private void showAlertItemDialog(List<ItemListRest> arrOrderItem, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_item_recycler, null);
        builder.setView(view);

        RecyclerView recyclerView = view.findViewById(R.id.rv_itemInfo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(mContext, arrOrderItem);
        recyclerView.setAdapter(itemAdapter);


        final Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Button button = view.findViewById(R.id.bt_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showAlertItemDialogLoundry(List<ItemListLoundry> arrOrderItem, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_item_recyclerloundry, null);
        builder.setView(view);

        RecyclerView recyclerView = view.findViewById(R.id.rv_itemInfo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(layoutManager);
        ItemAdapterLoundry itemAdapter = new ItemAdapterLoundry(mContext, arrOrderItem);
        recyclerView.setAdapter(itemAdapter);


        final Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Button button = view.findViewById(R.id.bt_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    class OrderListViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, otpId, orderDate, restaurantName, amount, paymentmode, delBoy, status, tvItemInfo, tvtypeName;
        LinearLayout ll2;
        ImageView imageViewColl;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            otpId = itemView.findViewById(R.id.otpId);
            orderDate = itemView.findViewById(R.id.orderDate);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            amount = itemView.findViewById(R.id.amount);
            paymentmode = itemView.findViewById(R.id.paymentmode);
            delBoy = itemView.findViewById(R.id.delBoy);
            status = itemView.findViewById(R.id.status);
            tvItemInfo = itemView.findViewById(R.id.itemInfo);
            imgYellow = itemView.findViewById(R.id.yello);
            imgGreen = itemView.findViewById(R.id.green);
            imageViewColl = itemView.findViewById(R.id.imgcash);
            imgRed = itemView.findViewById(R.id.red);
            tvtypeName = itemView.findViewById(R.id.typename);
            ll2 = itemView.findViewById(R.id.ll6);
        }
    }

    private String getPaymentStatus(int payment_id) {

        switch (payment_id) {
            case 0:
                return "Pending";
            case 1:
                return "Accepted by Restaurant";
            case 2:
                return "In Kitchen";
            case 3:
                return "Out for Delivery";
            case 4:
                return "Delivered";
            case 5:
                return "Canceled";

        }
        return "";
    }

    int getAnni(int payment_id) {
        Animation animation1 =
                AnimationUtils.loadAnimation(mContext,
                        R.anim.blink_amination);
        switch (payment_id) {
            case 0:
                imgRed.startAnimation(animation1);
                return 0;
            case 1:
                imgRed.startAnimation(animation1);
                return 1;
            case 2:
                imgYellow.startAnimation(animation1);
                return 2;
            case 3:
                imgYellow.startAnimation(animation1);
                return 3;
            case 4:
                imgGreen.startAnimation(animation1);
                return 4;
            case 5:
                imgGreen.startAnimation(animation1);
                return 5;

        }
        return 0;
    }


    private String getPaymentMode(int payment_id) {

        switch (payment_id) {
            case 1:
                return "Pay on delivery";
            case 2:
                return "Online";
        }
        return "";
    }

    private String changeDateFormat(String inputDateStr) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a, dd MMM yyyy");

        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }
}
