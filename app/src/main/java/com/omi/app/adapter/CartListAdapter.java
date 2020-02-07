package com.omi.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.activity.CartListActivity;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CardResponce;
import com.omi.app.models.CartItemLoundry;
import com.omi.app.models.ItemCartDetails;
import com.omi.app.models.LoginItems;
import com.omi.app.models.LondryCardResponce;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.SharePreferenceUtility;

import java.text.NumberFormat;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> implements RetrofitListener {
    private List<ItemCartDetails> arrItemCartDetails;
    private Context mContext;
    private ApiServiceProvider apiServiceProvider;
    private LoginItems mLoginItems;
    private RetrofitListener retrofitListener;
    private boolean IS_FROM_LAUNDRY = false;
    private List<CartItemLoundry> arrCartDataLoundries;
    private String rest_id, laundry_id;
    private Dialog dialog;
    private ItemCartDetails itemCartDetails;
    private CartItemLoundry cartItemLoundry;

    public CartListAdapter(List<ItemCartDetails> arrItemCartDetails, Context mContext, String rest_id, RetrofitListener retrofitListener) {
        this.arrItemCartDetails = arrItemCartDetails;
        this.mContext = mContext;
        this.rest_id = rest_id;
        this.retrofitListener = retrofitListener;
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
    }

    public CartListAdapter(List<CartItemLoundry> arrCartDataLoundries, Context mContext, RetrofitListener retrofitListener, String laundry_id, boolean IS_FROM_LAUNDRY) {
        this.arrCartDataLoundries = arrCartDataLoundries;
        this.mContext = mContext;
        this.laundry_id = laundry_id;
        this.retrofitListener = retrofitListener;
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        this.IS_FROM_LAUNDRY = IS_FROM_LAUNDRY;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.order_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!IS_FROM_LAUNDRY) {
            itemCartDetails = arrItemCartDetails.get(position);
            holder.textView.setText(itemCartDetails.itemName);
            if (arrItemCartDetails.get(position).itemType1.equals("Non-Veg")) {
                holder.imageView.setImageResource(R.drawable.bbn);
            } else {
                holder.imageView.setImageResource(R.drawable.veg);

            }
            holder.quantity.setText(itemCartDetails.qty);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            holder.tv_total_rice.setText("\u20B9 " + String.valueOf(Integer.parseInt(itemCartDetails.qty) * Integer.parseInt(itemCartDetails.price)));
            holder.tvrate.setText("\u20B9 " + String.valueOf(Integer.parseInt(itemCartDetails.price)));
        } else {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            cartItemLoundry = arrCartDataLoundries.get(position);
            holder.imageView.setVisibility(View.GONE);
            holder.textView.setText(cartItemLoundry.item.name);
            holder.quantity.setText(cartItemLoundry.qty);
            holder.tv_total_rice.setText("\u20B9 " + String.valueOf(Integer.parseInt(cartItemLoundry.qty) * Integer.parseInt(cartItemLoundry.price)));
            holder.tvrate.setText("\u20B9 " + String.valueOf(Integer.parseInt( cartItemLoundry.price)));
        }

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                int count = Integer.parseInt(holder.quantity.getText().toString());
                int ij = Integer.parseInt(holder.quantity.getText().toString());
                if (count == 0) {
                    holder.minus.setVisibility(View.VISIBLE);

                } else if (count == 1) {
                    count = ij - 1;
                    holder.quantity.setText(String.valueOf(count));
                } else {
                    count = ij - 1;
                    holder.quantity.setText(String.valueOf(count));
                }


                if (!IS_FROM_LAUNDRY) {
                    holder.tv_total_rice.setText(String.valueOf(count * Integer.parseInt(itemCartDetails.price)));
                    apiServiceProvider.getRequestRemoveFromCard(mLoginItems.id, rest_id, arrItemCartDetails.get(position).itemId, String.valueOf(1), arrItemCartDetails.get(position).price, CartListAdapter.this);
                } else {
                    holder.tv_total_rice.setText(String.valueOf(count * Integer.parseInt(cartItemLoundry.price)));
//                    apiServiceProvider.getRequestRemoveCardLoundry(mLoginItems.id, laundry_id, arrCartDataLoundries.get(position).item.id, String.valueOf(1), arrCartDataLoundries.get(position).price, CartListAdapter.this);
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                // call update to cart api
                int count = Integer.parseInt(holder.quantity.getText().toString());
                int ij = Integer.parseInt(holder.quantity.getText().toString());
                count = ij + 1;
                holder.quantity.setText(String.valueOf(count));

                if (!IS_FROM_LAUNDRY) {
                    holder.tv_total_rice.setText(String.valueOf(count * Integer.parseInt(itemCartDetails.price)));
                    apiServiceProvider.getRequestAddCard(mLoginItems.id, rest_id, arrItemCartDetails.get(position).itemId, String.valueOf(1), arrItemCartDetails.get(position).price, CartListAdapter.this);
                } else {
                    holder.tv_total_rice.setText(String.valueOf(count * Integer.parseInt(cartItemLoundry.price)));
//                    apiServiceProvider.getRequestAddCardLoundry(mLoginItems.id, laundry_id, arrCartDataLoundries.get(position).item.id, String.valueOf(1), arrCartDataLoundries.get(position).price, CartListAdapter.this);
                }
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceProvider.getUpdateAddCard(arrItemCartDetails.get(position).cartId, holder.quantity.getText().toString(), retrofitListener);
            }
        });


    }


    @Override
    public int getItemCount() {
        if (IS_FROM_LAUNDRY) {
            return arrCartDataLoundries.size();
        } else {
            return arrItemCartDetails.size();
        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {

        if (responseBody instanceof CardResponce) {
            CardResponce cardResponce = (CardResponce) responseBody;
            if (cardResponce.flag) {
                if (arrItemCartDetails.size() > 0) {
                    ((CartListActivity) mContext).onResume();
                }
                dialog.dismiss();
                Toast.makeText(mContext, cardResponce.data, Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof LondryCardResponce) {
            LondryCardResponce cardResponce = (LondryCardResponce) responseBody;
            if (cardResponce.flag) {
                dialog.dismiss();
                if (arrCartDataLoundries.size() > 0) {
                    ((CartListActivity) mContext).onResume();
                }
                Toast.makeText(mContext, cardResponce.data, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView, plus, minus;
        public TextView textView, tvrate, tv_total_rice, quantity, update;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.listmenu);
            this.textView = (TextView) itemView.findViewById(R.id.menuname);
            tvrate = (TextView) itemView.findViewById(R.id.tvrate1);
            tv_total_rice = (TextView) itemView.findViewById(R.id.tv_total_item);
            quantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            plus = itemView.findViewById(R.id.iv_plus);
            minus = itemView.findViewById(R.id.iv_minus);
            update = itemView.findViewById(R.id.tv_update);
        }
    }
}