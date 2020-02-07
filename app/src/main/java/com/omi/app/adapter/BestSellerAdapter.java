package com.omi.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omi.app.R;
import com.omi.app.activity.LoginActivity;
import com.omi.app.activity.RestaurantsActivity;
import com.omi.app.activity.RestaurantsDetailsActivity;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.BestSellerItem;
import com.omi.app.models.CartClearResponse;
import com.omi.app.models.LoginItems;
import com.omi.app.utils.Constants;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.ServiceViewHolder> implements RetrofitListener {
    private List<BestSellerItem> arrCity;
    private Context mContext;
    private String path = "http://kalpviksha.com/omi_demo/uploads/service/main_img/";
    private ApiServiceProvider apiServiceProvider;
    private LayoutInflater mLayoutInflater;
    String restaurant_id = "";
    LoginItems mLoginItems;

    String img_url = "";

    public BestSellerAdapter(List<BestSellerItem> arrCity, Context mContext, String restaurant_id) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        this.restaurant_id = restaurant_id;
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

    }

    public BestSellerAdapter(List<BestSellerItem> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        this.restaurant_id = "";
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_best_seller, viewGroup, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder cityViewHolder, final int position) {
        final BestSellerItem serviceItem = arrCity.get(position);

        cityViewHolder.tvId.setText(arrCity.get(position).id);
        cityViewHolder.service_name.setText(arrCity.get(position).name);
        if (checkRestaurantAvailability(arrCity.get(position).hrOfOperation)) {
            if (arrCity.get(position).closeByMerch.equals("Online")) {
                cityViewHolder.layout.setVisibility(View.GONE);
            } else {
                cityViewHolder.layout.setVisibility(View.VISIBLE);
            }
        } else {
            cityViewHolder.layout.setVisibility(View.VISIBLE);
        }
        img_url = Constants.AppConst.RESTAURANT_IMAGE_BASE_URL + arrCity.get(position).photo;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noimg);
        requestOptions.error(R.drawable.noimg);
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions).load(img_url).into(cityViewHolder.imageView);

        cityViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiServiceProvider.getRequestClearCart(mLoginItems.id, "restaurant", BestSellerAdapter.this);
                Intent i = new Intent(mContext, RestaurantsActivity.class);
                i.putExtra("rest_id", arrCity.get(position).id);
                i.putExtra("rest_name", arrCity.get(position).name);
                i.putExtra("rest_adr", arrCity.get(position).address);
                i.putExtra("img", img_url);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);


            }
        });
        cityViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Now close", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }

    private boolean checkRestaurantAvailability(String restaurant_time) {
        if (restaurant_time.contains(".")){

            String time[] = restaurant_time.split("to");
            String from_time = String.valueOf(time[0].split("\\.")[0]);
            String to_time = String.valueOf(time[1].split("\\.")[0]);
            int from = Integer.parseInt(from_time);
            int to = Integer.parseInt(to_time.trim());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if (hour <= to && hour >= from) {
                return true;
            }

        }else {
            String time[] = restaurant_time.split("to");
            String from_time = String.valueOf(time[0].split(":")[0]);
            String to_time = String.valueOf(time[1].split(":")[0]);
            int from = Integer.parseInt(from_time);
            int to = Integer.parseInt(to_time.trim());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if (hour <= to && hour >= from) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof CartClearResponse) {
            CartClearResponse cartClearResponse = (CartClearResponse) responseBody;


        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {


    }


    class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView service_name, tvId;
        RoundedImageView imageView;
        LinearLayout layout;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.tv_name);
            imageView = itemView.findViewById(R.id.img);
            tvId = itemView.findViewById(R.id.tv_id);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
