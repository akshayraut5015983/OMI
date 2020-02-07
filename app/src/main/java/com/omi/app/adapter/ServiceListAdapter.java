package com.omi.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.activity.LaundryServiceProviderActivity;
import com.omi.app.activity.LoginActivity;
import com.omi.app.activity.ResturentListActivity;
import com.omi.app.activity.ServicSubActivity;
import com.omi.app.models.CityItem;
import com.omi.app.models.LoginItems;
import com.omi.app.models.ServiceItem;
import com.omi.app.utils.SharePreferenceUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.omi.app.utils.Constants.AppConst.CITY_LOCATION;
import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceViewHolder> {
    private List<ServiceItem> arrCity;
    private Context mContext;

    private LayoutInflater mLayoutInflater;
    private String city_id = "";

    public ServiceListAdapter(List<ServiceItem> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_service, viewGroup, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder cityViewHolder, final int position) {
        final ServiceItem serviceItem = arrCity.get(position);
        cityViewHolder.tvId.setText(arrCity.get(position).id);
        cityViewHolder.service_name.setText(arrCity.get(position).name);
        Picasso.with(mContext).load("http://" + arrCity.get(position).image).into(cityViewHolder.imageView);
        cityViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginItems loginItems1 = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
                if (loginItems1 == null) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    ((Activity) mContext).finish();
                } else {
                    if (arrCity.get(position).name.equals("Restaurants")) {
                        Intent i = new Intent(mContext, ResturentListActivity.class);
                        i.putExtra("city_id", "0");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    } else if (arrCity.get(position).name.equals("Home Services")) {
                        Intent i = new Intent(mContext, ServicSubActivity.class);
                        i.putExtra("key", arrCity.get(position).id);
                        i.putExtra("name", "Home Services");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    } else if (arrCity.get(position).name.equals("OMI Laundry")) {
                        Intent i = new Intent(mContext, LaundryServiceProviderActivity.class);
                        i.putExtra("key", arrCity.get(position).id);
                        i.putExtra("name", "OMI Laundry");
                        i.putExtra("city_id", "0");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        /*}*/
                    }

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
