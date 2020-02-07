package com.omi.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.ItemInfo;
import com.omi.app.models.ItemListRest;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ItemListRest> arrOrder;

    public ItemAdapter(Context mContext, List<ItemListRest> arrOrder) {
        this.mContext = mContext;
        this.arrOrder = arrOrder;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_order_items, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        ItemListRest orderItemInfo = arrOrder.get(i);
        itemViewHolder.item_name.setText(orderItemInfo.name);
        itemViewHolder.item_quantity.setText(" x " + orderItemInfo.qty);
    }

    @Override
    public int getItemCount() {
        return arrOrder.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, item_quantity;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.tv_item_name);
            item_quantity = itemView.findViewById(R.id.tv_item_quantity);
        }
    }
}
