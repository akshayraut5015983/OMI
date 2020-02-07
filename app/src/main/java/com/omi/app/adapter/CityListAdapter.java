package com.omi.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.activity.HomeActivity;
import com.omi.app.models.CityItem;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.CITY_LOCATION;
import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LONGI;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {
    private List<CityItem> arrCity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean IS_FROM_CHECKOUT;

    public CityListAdapter(List<CityItem> arrCity, Context mContext, boolean IS_FROM_CHECKOUT) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        this.IS_FROM_CHECKOUT = IS_FROM_CHECKOUT;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_city, viewGroup, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CityViewHolder cityViewHolder, final int position) {
        final CityItem cityItem = arrCity.get(position);
        cityViewHolder.city_name.setText(cityItem.cityName);

    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }


    class CityViewHolder extends RecyclerView.ViewHolder {
        TextView city_name;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            city_name = itemView.findViewById(R.id.tv_city_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharePreferenceUtility.saveStringPreferences(mContext, LAT, "");
                    SharePreferenceUtility.saveStringPreferences(mContext, LONGI, "");

                    SharePreferenceUtility.saveCityPreferences(mContext, CITY_LOCATION, arrCity.get(getAdapterPosition()));
                        Intent i = new Intent(mContext, HomeActivity.class);
                        i.putExtra("key", arrCity.get(getAdapterPosition()).cityName);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);

                }
            });
        }
    }
}
