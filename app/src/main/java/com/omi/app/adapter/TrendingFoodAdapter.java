package com.omi.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.omi.app.R;
import com.omi.app.activity.LoginActivity;
import com.omi.app.activity.ResturentListActivity;
import com.omi.app.models.BestSellerItem;
import com.omi.app.models.LoginItems;
import com.omi.app.models.TrndingFoodItem;
import com.omi.app.utils.Constants;
import com.omi.app.utils.SharePreferenceUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class TrendingFoodAdapter extends RecyclerView.Adapter<TrendingFoodAdapter.ServiceViewHolder> {
    private List<TrndingFoodItem> arrCity;
    private Context mContext;
    private String path = "http://techpanion.in/omi_demo/uploads/service/main_img/";

    private LayoutInflater mLayoutInflater;

    public TrendingFoodAdapter(List<TrndingFoodItem> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_trend_food, viewGroup, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder cityViewHolder, final int position) {
        final TrndingFoodItem serviceItem = arrCity.get(position);
        cityViewHolder.tvId.setText(arrCity.get(position).id);
        cityViewHolder.service_name.setText(arrCity.get(position).name);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noimg);
        requestOptions.error(R.drawable.noimg);
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions).load(Constants.AppConst.HomePathMain + arrCity.get(position).itemPic).into(cityViewHolder.imageView);

//        Picasso.with(mContext).load(Constants.AppConst.HomePathMain + arrCity.get(position).itemPic).into(cityViewHolder.imageView);

        cityViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginItems loginItems1 = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
                if (loginItems1 == null) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, ResturentListActivity.class);
                    i.putExtra("city_id", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrCity.size();
    }


    class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView service_name, tvId;
        ImageView imageView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.tv_name);
            imageView = itemView.findViewById(R.id.img);
            tvId = itemView.findViewById(R.id.tv_id);
        }
    }
}
