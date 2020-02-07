package com.omi.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omi.app.R;
import com.omi.app.activity.RatingActivtiy;
import com.omi.app.activity.RestaurantsActivity;
import com.omi.app.activity.RestaurantsDetailsActivity;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CartClearResponse;
import com.omi.app.models.LoginItems;
import com.omi.app.models.ResturenListItem;
import com.omi.app.utils.Constants;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class RestureneListAdapter extends RecyclerView.Adapter<RestureneListAdapter.SubServiceHolder> implements RetrofitListener {
    private List<ResturenListItem> arrCity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ApiServiceProvider apiServiceProvider;
    String restaurant_id;
    private LoginItems mLoginItems;
    private int pos;

    public RestureneListAdapter(List<ResturenListItem> arrCity, Context mContext, String restaurant_id) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        this.restaurant_id = restaurant_id;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

    }

    public RestureneListAdapter(List<ResturenListItem> arrCity, Context mContext) {
        this.arrCity = arrCity;
        this.mContext = mContext;
        this.restaurant_id = "";
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        apiServiceProvider = ApiServiceProvider.getInstance(mContext);
        mLoginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

    }

    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_res_list, viewGroup, false);
        return new SubServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServiceHolder cityViewHolder, final int position) {
        ResturenListItem serviceItem = arrCity.get(position);
        cityViewHolder.service_name.setText(arrCity.get(position).name);
        cityViewHolder.addrss.setText(arrCity.get(position).address);
        cityViewHolder.other.setText(arrCity.get(position).otherFacility);
        cityViewHolder.mob.setText(arrCity.get(position).mobile);

        final String img_url = Constants.AppConst.RESTAURANT_IMAGE_BASE_URL + arrCity.get(position).photo;

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no_image);
        requestOptions.error(R.drawable.no_image);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions).load(img_url).into(cityViewHolder.imageView);

        if (checkRestaurantAvailability(arrCity.get(position).hrOfOperation)) {
            if (arrCity.get(position).closeByMerch.equals("Online")) {
                cityViewHolder.layout.setVisibility(View.GONE);
                cityViewHolder.menu.setVisibility(View.VISIBLE);
            } else {
                cityViewHolder.menu.setVisibility(View.GONE);
                cityViewHolder.layout.setVisibility(View.VISIBLE);
            }
        } else {
            cityViewHolder.layout.setVisibility(View.VISIBLE);
            cityViewHolder.menu.setVisibility(View.GONE);
        }
        cityViewHolder.tvRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrCity.get(position).closeByMerch.equals("Online")) {
                    Intent i = new Intent(mContext, RatingActivtiy.class);
                    i.putExtra("rest_id", arrCity.get(position).id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            }
        });
        cityViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrCity.get(position).closeByMerch.equals("Online")) {
                    Intent i = new Intent(mContext, RestaurantsDetailsActivity.class);
                    i.putExtra("rest_id", arrCity.get(position).id);
                    i.putExtra("name", arrCity.get(position).name);
                    i.putExtra("adr", arrCity.get(position).address);
                    i.putExtra("mob", arrCity.get(position).mobile);
                    i.putExtra("hr", arrCity.get(position).hrOfOperation);
                    i.putExtra("clo", arrCity.get(position).closeDay);
                    i.putExtra("dis", arrCity.get(position).description);
                    i.putExtra("ot", arrCity.get(position).otherFacility);
                    i.putExtra("img", arrCity.get(position).photo);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            }
        });

        cityViewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                if (restaurant_id.equals("") || restaurant_id.equals(arrCity.get(position).id)) {
                    if (arrCity.get(position).closeByMerch.equals("Online")) {
                        Intent i = new Intent(mContext, RestaurantsActivity.class);
                        i.putExtra("rest_id", arrCity.get(position).id);
                        i.putExtra("rest_name", arrCity.get(position).name);
                        i.putExtra("rest_adr", arrCity.get(position).address);
                        i.putExtra("img", img_url);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    } else {
                        Toast.makeText(mContext, "Right Now, Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Item has already added in cart. Due you want clear cart");
                    builder.setCancelable(false);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            apiServiceProvider.getRequestClearCart(mLoginItems.id, "restaurant", RestureneListAdapter.this);
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCity.size();
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof CartClearResponse) {
            CartClearResponse cartClearResponse = (CartClearResponse) responseBody;
            if (arrCity.get(pos).closeByMerch.equals("Online")) {
                Intent i = new Intent(mContext, RestaurantsActivity.class);
                i.putExtra("rest_id", arrCity.get(pos).id);
                i.putExtra("rest_name", arrCity.get(pos).name);
                i.putExtra("rest_adr", arrCity.get(pos).address);
                i.putExtra("img", arrCity.get(pos).photo);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            } else {
                Toast.makeText(mContext, "Right Now, Not Available..!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
    }


    class SubServiceHolder extends RecyclerView.ViewHolder {
        TextView service_name, addrss, other, mob, menu, status, tvRate;
        RoundedImageView imageView;
        LinearLayout layout;

        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.tv_name);
            addrss = itemView.findViewById(R.id.tv_adr);
            other = itemView.findViewById(R.id.tv_other);
            layout = itemView.findViewById(R.id.layer);
            mob = itemView.findViewById(R.id.tv_mob);
            menu = itemView.findViewById(R.id.menu);
            imageView = itemView.findViewById(R.id.img);
            status = itemView.findViewById(R.id.tv_status);
            tvRate = itemView.findViewById(R.id.rest_rate);
        }
    }


    private boolean checkRestaurantAvailability(String restaurant_time) {
        if (restaurant_time.contains(".")){

            String time[] = restaurant_time.split("to");
            String from_time = String.valueOf(time[0].split("\\.")[0]);
            String to_time = String.valueOf(time[1].split("\\.")[0]);
            int from = Integer.parseInt(from_time);
            int to = Integer.parseInt(to_time.trim());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if (hour <= to && hour >= from) {
                return true;
            }

        } else if (restaurant_time.contains("M")) {
            // 12:00 AM to 11:59 PM
            String time[] = restaurant_time.split("to");
            String from_time = String.valueOf(time[0].split(":")[0]);
            String tmp_from_time = "0";
            if (from_time.equals("12")) {
                tmp_from_time = "0";
            }
            String to_time = String.valueOf(time[1].split(":")[0]);
            int from = Integer.parseInt(tmp_from_time);
            int to = Integer.parseInt(to_time.trim()) + 12;
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if (hour <= to && hour >= from) {
                return true;
            }
        }else {
            String time[] = restaurant_time.split("to");
            String from_time = String.valueOf(time[0].split(":")[0]);
            String to_time = String.valueOf(time[1].split(":")[0]);
            int from = Integer.parseInt(from_time);
            int to = Integer.parseInt(to_time.trim());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if (hour <= to && hour >= from) {
                return true;
            }
        }
        return false;
    }
}
