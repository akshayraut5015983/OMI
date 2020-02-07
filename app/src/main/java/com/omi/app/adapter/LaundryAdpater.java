package com.omi.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.Laundry;

public class LaundryAdpater extends RecyclerView.Adapter<LaundryAdpater.ViewHolder> {
    private Laundry[] listdata;

    // RecyclerView recyclerView;
    public LaundryAdpater(Laundry[] listdata) {
        this.listdata = listdata;
    }

    @Override
    public LaundryAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.laudry_row, parent, false);
        LaundryAdpater.ViewHolder viewHolder = new LaundryAdpater.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LaundryAdpater.ViewHolder holder, int position) {
        final Laundry myListData = listdata[position];

    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1, textView2, textView3, textView4, textView5;


        public ViewHolder(View itemView) {
            super(itemView);

//            this.textView1 = (TextView) itemView.findViewById(R.id.tv1);
//            this.textView2 = (TextView) itemView.findViewById(R.id.tv2);
//            this.textView3 = (TextView) itemView.findViewById(R.id.tv3);
//            this.textView4 = (TextView) itemView.findViewById(R.id.tv4);
//            this.textView5 = (TextView) itemView.findViewById(R.id.tv5);
        }


    }
}

