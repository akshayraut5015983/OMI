package com.omi.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.ItemListLoundry;
import com.omi.app.models.ItemListRest;

import java.util.List;

public class ItemAdapterLoundry extends RecyclerView.Adapter<ItemAdapterLoundry.ItemViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ItemListLoundry> arrOrder;

    public ItemAdapterLoundry(Context mContext, List<ItemListLoundry> arrOrder) {
        this.mContext = mContext;
        this.arrOrder = arrOrder;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_order_itemslo, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        ItemListLoundry orderItemInfo = arrOrder.get(i);
        itemViewHolder.tvService.setText(orderItemInfo.oprValue);
        itemViewHolder.tvSubservice.setText(orderItemInfo.typeValue);
        itemViewHolder.item_name.setText(orderItemInfo.itemName);
        itemViewHolder.item_quantity.setText(" x " + orderItemInfo.qty);
    }

    @Override
    public int getItemCount() {
        return arrOrder.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, item_quantity, tvService, tvSubservice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.tv_item_name);
            item_quantity = itemView.findViewById(R.id.tv_item_quantity);
            tvService = itemView.findViewById(R.id.tvservice);
            tvSubservice = itemView.findViewById(R.id.tvsubservice);
        }
    }
}
