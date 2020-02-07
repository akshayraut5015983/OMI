package com.omi.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.activity.HomeActivityDetils;
import com.omi.app.models.HomeServiceItem;
import com.omi.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeServiceAdapter extends RecyclerView.Adapter<HomeServiceAdapter.SubServiceHolder> {
    private List<HomeServiceItem> arrCity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public HomeServiceAdapter(List<HomeServiceItem> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_home_service, viewGroup, false);
        return new SubServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServiceHolder cityViewHolder, final int position) {
        final HomeServiceItem serviceItem = arrCity.get(position);
        cityViewHolder.tvname.setText(arrCity.get(position).shopName);
        cityViewHolder.tvDisp.setText(arrCity.get(position).description);
        cityViewHolder.tvOther.setText(arrCity.get(position).otherFacility);
        cityViewHolder.tvMob.setText(arrCity.get(position).mobile);
        cityViewHolder.tvovName.setText(arrCity.get(position)._package);
        cityViewHolder.tvAdr.setText(arrCity.get(position).address);
        cityViewHolder.tvHr.setText(arrCity.get(position).hrOfOperation);
        Picasso.with(mContext).load(Constants.AppConst.HomeService + arrCity.get(position).photo).into(cityViewHolder.img);

        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeActivityDetils.class);
                intent.putExtra("name", arrCity.get(position).shopName);
                intent.putExtra("mob", arrCity.get(position).mobile);
                intent.putExtra("adr", arrCity.get(position).address);
                intent.putExtra("hr", arrCity.get(position).hrOfOperation);
                intent.putExtra("disp", arrCity.get(position).description);
                intent.putExtra("other", arrCity.get(position).otherFacility);
                intent.putExtra("img",arrCity.get(position).photo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }

    class SubServiceHolder extends RecyclerView.ViewHolder {
        TextView tvname, tvMob, tvDisp, tvOther, tvovName, tvAdr, tvHr;
        CircleImageView img;
        ImageView coll;

        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tv_name);
            tvMob = itemView.findViewById(R.id.tv_mob);
            tvDisp = itemView.findViewById(R.id.tv_disp);
            tvOther = itemView.findViewById(R.id.tv_other);
            tvHr = itemView.findViewById(R.id.tv_hr);
            tvovName = itemView.findViewById(R.id.tv_owname);
            tvAdr = itemView.findViewById(R.id.tv_adr);
            img = itemView.findViewById(R.id.photo);
            coll = itemView.findViewById(R.id.img_coll);
        }
    }
}
