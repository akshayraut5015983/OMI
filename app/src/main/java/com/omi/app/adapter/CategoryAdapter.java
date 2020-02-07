package com.omi.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {
    private List<String> arrCategory;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    int row_index = -1;

    public CategoryAdapter(List<String> arrCategory, Context mContext) {
        this.arrCategory = arrCategory;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_category, viewGroup, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int i) {
        holder.category_name.setText(arrCategory.get(i));
    }

    @Override
    public int getItemCount() {
        return arrCategory.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.tv_cat_name);
        }
    }


}
