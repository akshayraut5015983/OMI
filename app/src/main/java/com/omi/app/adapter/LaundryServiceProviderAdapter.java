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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omi.app.R;
import com.omi.app.utils.SharePreferenceUtility;
import com.omi.app.activity.LoundryService;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartClearResponse;
import com.omi.app.models.LoginItems;
import com.omi.app.models.SubServiceLoundryItem;
import com.omi.app.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class LaundryServiceProviderAdapter extends RecyclerView.Adapter<com.omi.app.adapter.LaundryServiceProviderAdapter.SubServiceHolder> implements RetrofitListener {
    private List<SubServiceLoundryItem> arrCity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String laundry_id;
    private ApiServiceProvider apiServiceProvider;
    private LoginItems mLoginItems;
    private int pos;

    public LaundryServiceProviderAdapter(List<SubServiceLoundryItem> arrCity, Context mContext, String laundry_id) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        this.laundry_id = laundry_id;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

    }

    public LaundryServiceProviderAdapter(List<SubServiceLoundryItem> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        this.laundry_id = "";
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

    }

    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_sub_service_loundry, viewGroup, false);
        return new SubServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServiceHolder cityViewHolder, final int position) {

        cityViewHolder.tvName.setText(arrCity.get(position).dhobiName);
        cityViewHolder.tvAdr.setText(arrCity.get(position).address);
        cityViewHolder.tvMob.setText(arrCity.get(position).mobile);
        cityViewHolder.tvHroperation.setText(arrCity.get(position).hrOfOperation);
        cityViewHolder.tvdisp.setText(arrCity.get(position).description);
        String img_url = Constants.AppConst.LoundryHome + arrCity.get(position).photo;
        Glide.with(mContext).load(img_url).into(cityViewHolder.imageView);
        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                if (laundry_id.equals("")) {
                    Intent i = new Intent(mContext, LoundryService.class);
                    i.putExtra("id", arrCity.get(position).id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                } else if (laundry_id.equals(arrCity.get(position).id)) {
                    Intent i = new Intent(mContext, LoundryService.class);
                    i.putExtra("id", arrCity.get(position).id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Item has already added in cart. Due you want clear cart");
                    builder.setCancelable(false);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            apiServiceProvider.getRequestClearCart(mLoginItems.id, "restaurant", LaundryServiceProviderAdapter.this);
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof CartClearResponse) {
            CartClearResponse cartClearResponse = (CartClearResponse) responseBody;
            if (arrCity.get(pos).closeByMerch.equals("Online")) {
                Intent i = new Intent(mContext, LoundryService.class);
                i.putExtra("id", arrCity.get(pos).id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            } else {
                Toast.makeText(mContext, "Right Now, Not Available..!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(com.omi.app.utils.ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }


    class SubServiceHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMob, tvAdr, tvHroperation, tvdisp;
        RoundedImageView imageView;

        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMob = itemView.findViewById(R.id.tv_mob);
            tvAdr = itemView.findViewById(R.id.tv_adr);
            tvHroperation = itemView.findViewById(R.id.tv_hr);
            tvdisp = itemView.findViewById(R.id.tv_disp);
            imageView = itemView.findViewById(R.id.photo);
        }
    }
}
