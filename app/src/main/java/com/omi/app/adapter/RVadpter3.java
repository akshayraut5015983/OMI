package com.omi.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.ImageModel;

public class RVadpter3 extends RecyclerView.Adapter<RVadpter3.ViewHolder>{
    private ImageModel[] listdata;

    // RecyclerView recyclerView;
    public RVadpter3(ImageModel[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public RVadpter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.rvrow3, parent, false);
        RVadpter3.ViewHolder viewHolder = new RVadpter3.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVadpter3.ViewHolder holder, int position) {
        final ImageModel myListData = listdata[position];
        holder.textView.setVisibility(View.VISIBLE);
        holder.textView.setText(listdata[position].getName());
        holder.imageView.setImageResource(listdata[position].getImage());
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.img);
            this.textView = (TextView) itemView.findViewById(R.id.tv_hotel_name);

        }
    }
}


