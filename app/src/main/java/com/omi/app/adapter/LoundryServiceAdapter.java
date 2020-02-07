package com.omi.app.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omi.app.R;
import com.omi.app.activity.LoundryDetails;
import com.omi.app.utils.SharePreferenceUtility;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CardResponce;
import com.omi.app.models.LondryCardResponce;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.LoginItems;
import com.omi.app.models.LoundryTypeItem;
import com.omi.app.utils.Constants;

import java.text.NumberFormat;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class LoundryServiceAdapter extends RecyclerView.Adapter<LoundryServiceAdapter.SubServiceHolder> implements RetrofitListener {
    private List<LoundryTypeItem> arrLoundryTypeItems;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ApiServiceProvider apiServiceProvider;
    private String loundryId;
    LoginItems loginItems;
    private Dialog dialog;
    ;
    String operation_type;

    public LoundryServiceAdapter(List<LoundryTypeItem> arrLoundryTypeItems, Context mContext, ApiServiceProvider apiServiceProvider, String loundryId, Dialog dialog, String operation_type) {
        this.arrLoundryTypeItems = arrLoundryTypeItems;
        this.operation_type = operation_type;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.apiServiceProvider = apiServiceProvider;
        this.loundryId = loundryId;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_loundry_service, viewGroup, false);
        return new SubServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServiceHolder cityViewHolder, final int position) {
        final LoundryTypeItem serviceItem = arrLoundryTypeItems.get(position);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        cityViewHolder.tvname.setText(arrLoundryTypeItems.get(position).itemName);
        cityViewHolder.tvValue.setText(arrLoundryTypeItems.get(position).itemTypeValue);
        cityViewHolder.tvIron.setText(String.valueOf(Integer.parseInt(arrLoundryTypeItems.get(position).price)));
        String img_url = Constants.AppConst.LoundryHome_item + arrLoundryTypeItems.get(position).itemPic;
        Glide.with(mContext).load(img_url).into(cityViewHolder.imageView);

        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

        cityViewHolder.mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginItems == null) {
                    Toast.makeText(mContext, "Please Login", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    // call add to cart api
//                    Toast.makeText(mContext, loginItems.id + " " + qty + " " + arrResturenMenuItems.get(position).id, Toast.LENGTH_LONG).show();
                    cityViewHolder.mBtAdd.setVisibility(View.GONE);
                    cityViewHolder.plus.setVisibility(View.VISIBLE);
                    apiServiceProvider.getRequestAddCardLoundry(loginItems.id, loundryId, arrLoundryTypeItems.get(position).itemId, String.valueOf(1), arrLoundryTypeItems.get(position).price, operation_type, LoundryServiceAdapter.this);
                }

            }
        });

        cityViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceProvider.getRequestRemoveCardLoundry(loginItems.id, loundryId, arrLoundryTypeItems.get(position).itemId, String.valueOf(1), arrLoundryTypeItems.get(position).price, operation_type, LoundryServiceAdapter.this);

                int count = Integer.parseInt(cityViewHolder.tv_quantity.getText().toString());
                int ij = Integer.parseInt(cityViewHolder.tv_quantity.getText().toString());
                if (count == 1) {
                    cityViewHolder.mBtAdd.setVisibility(View.VISIBLE);
                } else {
                    count = ij - 1;
                    cityViewHolder.tv_quantity.setText(String.valueOf(count));
                }
            }
        });

        cityViewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                // call update to cart api
                int count = Integer.parseInt(cityViewHolder.tv_quantity.getText().toString());
                int ij = Integer.parseInt(cityViewHolder.tv_quantity.getText().toString());
                count = ij + 1;
                cityViewHolder.tv_quantity.setText(String.valueOf(count));
                apiServiceProvider.getRequestAddCardLoundry(loginItems.id, loundryId, arrLoundryTypeItems.get(position).itemId, String.valueOf(1), arrLoundryTypeItems.get(position).price, operation_type, LoundryServiceAdapter.this);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrLoundryTypeItems.size();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof CardResponce) {
            CardResponce cardResponce = (CardResponce) responseBody;
            if (cardResponce.flag) {
//                Toast.makeText(mContext, cardResponce.data, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof LondryCardResponce) {
            LondryCardResponce londryCardResponce = (LondryCardResponce) responseBody;
            if (londryCardResponce.flag) {
                ((LoundryDetails) mContext).onResume();
//                Toast.makeText(mContext, londryCardResponce.data, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {

    }

    class SubServiceHolder extends RecyclerView.ViewHolder {
        TextView tvname, tvValue, tvIron, tvWash, tvIronWas, tvDryClean, tv_quantity;
        ImageView imageView, minus, plus;
        Button mBtAdd;

        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tv_name);
            tvValue = itemView.findViewById(R.id.tv_val);
            tvIron = itemView.findViewById(R.id.tv_iron);
            tvWash = itemView.findViewById(R.id.tv_wash);
            tvIronWas = itemView.findViewById(R.id.tv_iron_wash);
            tvDryClean = itemView.findViewById(R.id.tv_dry_clean);
            imageView = itemView.findViewById(R.id.img);
            minus = itemView.findViewById(R.id.img_minus);
            plus = itemView.findViewById(R.id.img_add);
            tv_quantity = itemView.findViewById(R.id.tv_qty);
            mBtAdd = itemView.findViewById(R.id.btn_add);
        }
    }
}
