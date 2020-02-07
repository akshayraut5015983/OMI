package com.omi.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.ImageModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder>{
    private ImageModel[] listdata;

    // RecyclerView recyclerView;
    public CircleAdapter(ImageModel[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public CircleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.circle_row, parent, false);
        CircleAdapter.ViewHolder viewHolder = new CircleAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CircleAdapter.ViewHolder holder, int position) {
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
        public CircleImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (CircleImageView) itemView.findViewById(R.id.img);
            this.textView = (TextView) itemView.findViewById(R.id.tv_hotel_name);

        }
    }
}

