package com.omi.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.omi.app.R;
import com.omi.app.activity.HomeActivity;
import com.omi.app.activity.RatingActivtiy;
import com.omi.app.models.CityItem;
import com.omi.app.models.RattingList;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.CITY_LOCATION;
import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LONGI;

public class RattingListAdapter extends RecyclerView.Adapter<RattingListAdapter.CityViewHolder> {
    private List<RattingList> arrCity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean IS_FROM_CHECKOUT;


    public RattingListAdapter(List<RattingList> arrCity, RatingActivtiy mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_comment, viewGroup, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CityViewHolder cityViewHolder, final int position) {
        final RattingList cityItem = arrCity.get(position);
        cityViewHolder.city_name.setText(cityItem.userName);
        cityViewHolder.tvDate.setText(cityItem.date);
        cityViewHolder.tvCmt.setText(cityItem.review);
        cityViewHolder.simpleRatingBar.setRating(Float.parseFloat(arrCity.get(position).ratingCount));

    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }


    class CityViewHolder extends RecyclerView.ViewHolder {
        TextView city_name,tvDate,tvCmt;
        SimpleRatingBar simpleRatingBar;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            city_name = itemView.findViewById(R.id.tv_city_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvCmt = itemView.findViewById(R.id.tv_cmt);
            simpleRatingBar = itemView.findViewById(R.id.rating_bar);

        }
    }
}
