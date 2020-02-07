package com.omi.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.Clothes;

public class LaundryAdapter extends RecyclerView.Adapter<LaundryAdapter.ViewHolder>{
    private Clothes[] listdata;

    // RecyclerView recyclerView;
    public LaundryAdapter(Clothes[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public LaundryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.tab_laundry_rv_row, parent, false);
        LaundryAdapter.ViewHolder viewHolder = new LaundryAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LaundryAdapter.ViewHolder holder, int position) {
        final Clothes myListData = listdata[position];

//        holder.textView1.setText(listdata[position].getName());
//        holder.menuprice.setText(""+listdata[position].getPrice());
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView,textView1,menuprice;

        public ViewHolder(View itemView) {
            super(itemView);
           // this.textView1 = (TextView) itemView.findViewById(R.id.paymentmode);


        }}
}
