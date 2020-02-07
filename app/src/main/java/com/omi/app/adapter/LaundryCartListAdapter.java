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
import com.omi.app.utils.SharePreferenceUtility;
import com.omi.app.activity.CartListActivity;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartItemLoundry;
import com.omi.app.models.LoginItems;
import com.omi.app.models.LondryCardResponce;
import com.omi.app.utils.ErrorObject;

import java.text.NumberFormat;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class LaundryCartListAdapter extends RecyclerView.Adapter<LaundryCartListAdapter.LaundryCartViewHolder> implements RetrofitListener {

    private List<CartItemLoundry> cartItemLoundries;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Dialog dialog;
    private LoginItems mLoginItems;
    private CartItemLoundry cartItemLoundry;
    private ApiServiceProvider apiServiceProvider;
    private String laundry_id;

    public LaundryCartListAdapter(List<CartItemLoundry> cartItemLoundries, Context mContext, String laundry_id) {
        this.cartItemLoundries = cartItemLoundries;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.laundry_id = laundry_id;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

    }

    @NonNull
    @Override
    public LaundryCartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.laudry_row, viewGroup, false);
        return new LaundryCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LaundryCartViewHolder holder, final int i) {
        cartItemLoundry = cartItemLoundries.get(i);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        holder.item_name.setText(cartItemLoundry.item.name);
        holder.item_type.setText(cartItemLoundry.item_opr_val);
        holder.item_price.setText(format.format(Integer.parseInt(cartItemLoundry.price)));
        holder.tv_human_type.setText(cartItemLoundry.item.tValue);
        holder.tv_quantity.setText(cartItemLoundry.qty);

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                int count = Integer.parseInt(holder.tv_quantity.getText().toString());
                int ij = Integer.parseInt(holder.tv_quantity.getText().toString());
                if (count == 0) {
                    holder.minus.setVisibility(View.VISIBLE);


                } else if (count == 1) {
                    count = ij - 1;
                    holder.tv_quantity.setText(String.valueOf(count));
                } else {
                    count = ij - 1;
                    holder.tv_quantity.setText(String.valueOf(count));
                }


                holder.item_price.setText(String.valueOf(count * Integer.parseInt(cartItemLoundry.price)));
                apiServiceProvider.getRequestRemoveCardLoundry(mLoginItems.id, laundry_id, cartItemLoundries.get(i).item.id, String.valueOf(1), cartItemLoundries.get(i).price,cartItemLoundries.get(i).item_opr_val, LaundryCartListAdapter.this);

            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                // call update to cart api
                int count = Integer.parseInt(holder.tv_quantity.getText().toString());
                int ij = Integer.parseInt(holder.tv_quantity.getText().toString());
                count = ij + 1;
                holder.tv_quantity.setText(String.valueOf(count));

                holder.item_price.setText(String.valueOf(count * Integer.parseInt(cartItemLoundry.price)));
                apiServiceProvider.getRequestAddCardLoundry(mLoginItems.id, laundry_id, cartItemLoundries.get(i).item.id, String.valueOf(1), cartItemLoundries.get(i).price,cartItemLoundries.get(i).item_opr_val, LaundryCartListAdapter.this);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItemLoundries.size();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof LondryCardResponce) {
            LondryCardResponce cardResponce = (LondryCardResponce) responseBody;
            if (cardResponce.flag) {
                dialog.dismiss();
                if (cartItemLoundries.size() > 0) {
                    ((CartListActivity) mContext).onResume();
                }
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {

    }

    public class LaundryCartViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, item_type, item_price, tv_human_type,
                tv_quantity;
        ImageView plus, minus;

        public LaundryCartViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.tv_item_name);
            item_type = itemView.findViewById(R.id.tv_type);
            item_price = itemView.findViewById(R.id.tv_price);
            tv_human_type = itemView.findViewById(R.id.tv_human_type);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            plus = itemView.findViewById(R.id.iv_plus);
            minus = itemView.findViewById(R.id.iv_minus);
        }
    }

    private String getLaundryCart(int id) {
        switch (id) {
            case 1:
                return "Iron";
            case 2:
                return "Wash";
            case 3:
                return "Iron Wash";
            case 4:
                return "Dry Cleaning";
            case 5:
                return "Wash per kg";

        }
        return "";
    }
}
