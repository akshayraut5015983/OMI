package com.omi.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.omi.app.R;
import com.omi.app.activity.LaundryServiceProviderActivity;
import com.omi.app.activity.ResturentListActivity;
import com.omi.app.activity.ServicSubActivity;
import com.omi.app.models.ServiceItem;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BannerPagerAdapter extends PagerAdapter {
    Context mContext;
    List<ServiceItem> categoryArrayList;
    LayoutInflater mLayoutInflater;
    Button go;

    public BannerPagerAdapter(Context m_Context, List<ServiceItem> categoryArrayList) {
        this.mContext = m_Context;
        this.categoryArrayList = categoryArrayList;
        mLayoutInflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if (categoryArrayList != null)
            return categoryArrayList.size();
        else
            return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
//        View itemView = (View) mLayoutInflater.inflate(R.layout.item_service_banner, null);
        View itemView = mLayoutInflater.inflate(R.layout.item_service_banner, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);
        String url = "http://" + categoryArrayList.get(position).imageBanner;
        Picasso.with(mContext)
                .load("http://" + categoryArrayList.get(position).imageBanner)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (categoryArrayList.get(position).name.equals("Home Services")) {
                    Intent i = new Intent(mContext, ServicSubActivity.class);
                    i.putExtra("key", categoryArrayList.get(position).id);
                    i.putExtra("name", "Home Services");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);

                } else if (categoryArrayList.get(position).name.equals("Restaurants")) {
                    Intent i = new Intent(mContext, ResturentListActivity.class);
                    i.putExtra("city_id", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);

                } else if (categoryArrayList.get(position).name.equals("OMI Laundry")) {
                    Intent i = new Intent(mContext, LaundryServiceProviderActivity.class);
                    i.putExtra("key", categoryArrayList.get(position).id);
                    i.putExtra("name", "OMI Laundry");
                    i.putExtra("city_id", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            }
        });
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

}

