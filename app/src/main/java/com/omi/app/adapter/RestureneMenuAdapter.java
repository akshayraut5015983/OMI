package com.omi.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.activity.RestaurantsActivity;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CardResponce;
import com.omi.app.models.LoginItems;
import com.omi.app.models.RestaurantItemDetails;
import com.omi.app.utils.SharePreferenceUtility;

import java.text.NumberFormat;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class RestureneMenuAdapter extends RecyclerView.Adapter<RestureneMenuAdapter.SubServiceHolder> implements RetrofitListener {
    private List<RestaurantItemDetails> arrResturenMenuItems;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ApiServiceProvider apiServiceProvider;
    private RetrofitListener retrofitListener;
    private int qty = 2;
    private String rest_id, user_id;
    private LoginItems loginItems;
    private Dialog dialog;

    public RestureneMenuAdapter(List<RestaurantItemDetails> arrResturenMenuItems, Context mContext, RetrofitListener retrofitListener, String rest_id) {
        this.arrResturenMenuItems = arrResturenMenuItems;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        this.retrofitListener = retrofitListener;
        this.rest_id = rest_id;
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        user_id = loginItems.id;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_res_menu, viewGroup, false);
        return new SubServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServiceHolder cityViewHolder, final int position) {
        final RestaurantItemDetails serviceItem = arrResturenMenuItems.get(position);
        cityViewHolder.service_name.setText(arrResturenMenuItems.get(position).name);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        cityViewHolder.price.setText("\u20B9 " + String.valueOf(Integer.parseInt(arrResturenMenuItems.get(position).price)));

        if (arrResturenMenuItems.get(position).type1.equals("Non-Veg")) {
            cityViewHolder.img_type.setImageResource(R.drawable.bbn);
        } else {
            cityViewHolder.img_type.setImageResource(R.drawable.veg);

        }
       /* cityViewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginItems loginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
                if (loginItems == null) {
                    Toast.makeText(mContext, "Please Login", Toast.LENGTH_SHORT).show();
                } else {
                    // call add to cart api
//                    Toast.makeText(mContext, loginItems.id + " " + qty + " " + arrResturenMenuItems.get(position).id, Toast.LENGTH_LONG).show();
//                    cityViewHolder.btn.setVisibility(View.GONE);
                    dialog.show();
                    apiServiceProvider.getRequestAddCard(user_id, rest_id, arrResturenMenuItems.get(position).id, String.valueOf(1), arrResturenMenuItems.get(position).price, RestureneMenuAdapter.this);
                }


            }
        });*/

        cityViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cityViewHolder.qty.getText().toString().equals("0")){
                    Toast.makeText(mContext, "Minimum value reached", Toast.LENGTH_SHORT).show();
                }else {
                    int count = Integer.parseInt(cityViewHolder.qty.getText().toString());
                    int ij = Integer.parseInt(cityViewHolder.qty.getText().toString());
                /*if (count == 1) {
//                    cityViewHolder.btn.setVisibility(View.VISIBLE);
                }else*/
                    if (count == 0) {

                    } else {
                        count = ij - 1;
                        cityViewHolder.qty.setText(String.valueOf(count));
                    }

                    dialog.show();
                    apiServiceProvider.getRequestRemoveFromCard(user_id, rest_id, arrResturenMenuItems.get(position).id, String.valueOf(1), arrResturenMenuItems.get(position).price, RestureneMenuAdapter.this);
                }
            }
        });

        cityViewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                // call update to cart api
                int count = Integer.parseInt(cityViewHolder.qty.getText().toString());
                int ij = Integer.parseInt(cityViewHolder.qty.getText().toString());
                count = ij + 1;
                cityViewHolder.qty.setText(String.valueOf(count));
                apiServiceProvider.getRequestAddCard(user_id, rest_id, arrResturenMenuItems.get(position).id, String.valueOf(1), arrResturenMenuItems.get(position).price, RestureneMenuAdapter.this);
            }
        });

        cityViewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceProvider.getRequestAddCard(user_id, rest_id, arrResturenMenuItems.get(position).id, cityViewHolder.qty.getText().toString(), arrResturenMenuItems.get(position).price, RestureneMenuAdapter.this);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrResturenMenuItems.size();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof CardResponce) {
            CardResponce cardResponce = (CardResponce) responseBody;
            if (cardResponce.flag) {
                ((RestaurantsActivity)mContext).onResume();
               // Toast.makeText(mContext, cardResponce.data, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(com.omi.app.utils.ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
    }

    class SubServiceHolder extends RecyclerView.ViewHolder {
        TextView service_name, price, qty, update;
        ImageView imageView, img_type, minus, plus;
//        Button btn;

        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.img);
            img_type = itemView.findViewById(R.id.imgtyp);
            qty = itemView.findViewById(R.id.tv_qty);
//            btn = itemView.findViewById(R.id.btn_add);
            plus = itemView.findViewById(R.id.img_add);
            minus = itemView.findViewById(R.id.img_minus);
            update = itemView.findViewById(R.id.tv_update);
        }
    }
}
