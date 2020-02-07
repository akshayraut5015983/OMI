package com.omi.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.activity.Cart2Activity;
import com.omi.app.models.Hotel;

public class MyHotelAdapter extends RecyclerView.Adapter<MyHotelAdapter.ViewHolder>{
    private Hotel[] listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyHotelAdapter(Hotel[] listdata, Context context) {
        this.listdata = listdata;
        this.context= context;
    }
    @Override
    public MyHotelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.hotel_row, parent, false);
        MyHotelAdapter.ViewHolder viewHolder = new MyHotelAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyHotelAdapter.ViewHolder holder, int position) {
        final Hotel myListData = listdata[position];
        holder.textView.setText(listdata[position].getName());
        holder.imageView.setImageResource(listdata[position].getImg());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(context, Cart2Activity.class);
                context.startActivity(i);
            }
        });
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