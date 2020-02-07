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
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.activity.HomeService;
import com.omi.app.activity.OrderActivity;
import com.omi.app.activity.RestaurantsActivity;
import com.omi.app.activity.ServicSubActivity;
import com.omi.app.models.ServiceItem;
import com.omi.app.models.SubServiceItem;
import com.omi.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubServiceAdapter extends RecyclerView.Adapter<SubServiceAdapter.SubServiceHolder> {
    private List<SubServiceItem> arrCity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
//    private String path = "http://techpanion.in/omi_demo/uploads/service/main_img/";

    public SubServiceAdapter(List<SubServiceItem> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_sub_service, viewGroup, false);
        return new SubServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServiceHolder cityViewHolder, final int position) {
        final SubServiceItem serviceItem = arrCity.get(position);
        cityViewHolder.service_name.setText(arrCity.get(position).name);
        Picasso.with(mContext).load(Constants.AppConst.HomePathMain + arrCity.get(position).image).into(cityViewHolder.imageView);


        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, HomeService.class);
                i.putExtra("id", arrCity.get(position).id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }


    class SubServiceHolder extends RecyclerView.ViewHolder {
        TextView service_name;
        ImageView imageView;

        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.tv_name);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
