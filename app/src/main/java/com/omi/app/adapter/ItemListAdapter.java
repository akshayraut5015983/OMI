package com.omi.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.ItemInfo;

import java.util.List;


public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.SubServiceHolder> {
    private List<ItemInfo> arrCity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String statusA = "Active", statusB = "Deactive";
    private int num = 0;

    public ItemListAdapter(List<ItemInfo> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_test, viewGroup, false);
        return new SubServiceHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final SubServiceHolder cityViewHolder, final int position) {
        final ItemInfo serviceItem = arrCity.get(position);
        cityViewHolder.tvQty.setText(arrCity.get(position).name);
        cityViewHolder.tvprice.setText(arrCity.get(position).qty);
    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }

    class SubServiceHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvprice, tvQty, tvTypeA, tvTypeB, tvToatl;

        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvprice = itemView.findViewById(R.id.tv_prc);
            tvQty = itemView.findViewById(R.id.tv_qty);
            tvTypeA = itemView.findViewById(R.id.typea);
            tvTypeB = itemView.findViewById(R.id.typeb);
            tvToatl = itemView.findViewById(R.id.total);
        }
    }
}
