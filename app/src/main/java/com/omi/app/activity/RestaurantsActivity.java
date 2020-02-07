package com.omi.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.omi.app.R;
import com.omi.app.adapter.CategoryAdapter;
import com.omi.app.adapter.RestureneMenuAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.CardResponce;
import com.omi.app.models.CartListResponce;
import com.omi.app.models.LoginItems;
import com.omi.app.models.ResturantCategoryMenu;
import com.omi.app.models.ResturentMenuResponce;
import com.omi.app.utils.Constants;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.RecyclerItemClickListener;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.ArrayList;
import java.util.List;

import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class RestaurantsActivity extends AppCompatActivity implements RetrofitListener {
    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRecyclerView, mRvCategoryList;
    String rest_id, tr, restName, image;
    TextView tvName;
    List<ResturantCategoryMenu> testServices;
    ImageView imageView;
    private Dialog dialog;
    private Context mContext;
    private LoginItems loginItems;
    private CardResponce cardResponce;
    private List<String> arrCat;
    private RelativeLayout mRlCartDetails;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ResturentMenuResponce serviceResponce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaturunt);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mContext = RestaurantsActivity.this;
        tvName = findViewById(R.id.tv_name);
        imageView = findViewById(R.id.img);
        mRvCategoryList = findViewById(R.id.rv_category);
        mRlCartDetails = findViewById(R.id.rl_view_card);

        mRvCategoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            rest_id = extra.getString("rest_id");
            restName = extra.getString("rest_name");
            image = extra.getString("img");
        }
        tvName.setText(restName);
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(mContext, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        /*final String img_url = Constants.AppConst.RESTAURANT_IMAGE_BASE_URL + image;

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no_image);
        requestOptions.error(R.drawable.no_image);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions).load(img_url).into(imageView);*/


        Glide.with(mContext).load(image).into(imageView);

        apiServiceProvider = ApiServiceProvider.getInstance(this);
        mRecyclerView = findViewById(R.id.rcylersubservice);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        ((TextView) findViewById(R.id.view_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantsActivity.this, CartListActivity.class);
                startActivity(i);

            }
        });

        mRvCategoryList.addOnItemTouchListener(new RecyclerItemClickListener(RestaurantsActivity.this, mRvCategoryList, new RecyclerItemClickListener.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(View view, int position) {
                RestureneMenuAdapter restureneMenuAdapter = new RestureneMenuAdapter(serviceResponce.data.get(position).items, RestaurantsActivity.this, RestaurantsActivity.this, rest_id);
                mRecyclerView.setAdapter(restureneMenuAdapter);


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        if (NetworkUtils.isNetworkConnected(RestaurantsActivity.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            dialog.show();
            apiServiceProvider.getRequestResturentMenu(rest_id, tr, RestaurantsActivity.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }

        if (NetworkUtils.isNetworkConnected(RestaurantsActivity.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            dialog.show();
            apiServiceProvider.getRequestResturentMenu(rest_id, tr, RestaurantsActivity.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }
    }

    public void ratting(View view) {
        Intent i = new Intent(mContext, RatingActivtiy.class);
        i.putExtra("rest_id", rest_id);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRlCartDetails.setVisibility(View.GONE);
        if (NetworkUtils.isNetworkConnected(RestaurantsActivity.this)) {
//                CommonUtils.showLoadingDialog(Login.this).show();
            apiServiceProvider.getRequestCartList(loginItems.id, RestaurantsActivity.this);
            dialog.show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {

        dialog.dismiss();
        if (responseBody instanceof ResturentMenuResponce) {

            serviceResponce = (ResturentMenuResponce) responseBody;
            if (serviceResponce.data != null) {
                if (serviceResponce.data.size() > 0) {

                    arrCat = new ArrayList<>();

                    for (int i = 0; i < serviceResponce.data.size(); i++) {
                        arrCat.add(serviceResponce.data.get(i).type2);
                    }
                    List<String> final_cat = removeDuplicates(arrCat);
                    CategoryAdapter categoryAdapter = new CategoryAdapter(final_cat, RestaurantsActivity.this);
                    mRvCategoryList.setAdapter(categoryAdapter);


                    RestureneMenuAdapter restureneMenuAdapter = new RestureneMenuAdapter(serviceResponce.data.get(0).items, RestaurantsActivity.this, RestaurantsActivity.this, rest_id);
                    mRecyclerView.setAdapter(restureneMenuAdapter);

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("No Menu items Available");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder.show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No Menu items Available");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
            }
        } else if (responseBody instanceof CartListResponce) {
            CartListResponce cartListResponce = (CartListResponce) responseBody;
            if (cartListResponce.flag) {
                if (cartListResponce.itemData.size() > 0) {
                    mRlCartDetails.setVisibility(View.VISIBLE);
                } else {
                    mRlCartDetails.setVisibility(View.GONE);
                }
                ((TextView) findViewById(R.id.tv_cart_details)).setText(String.valueOf(cartListResponce.itemData.size()) + "Item " + "| \u20B9 " + cartListResponce.priceDetails.totalAmount);

            }
        }

    }

    private void showAlertDialogForAlreadyAddedItems(String name) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("You have already added item in " + name);
        builder.setMessage("Do you want to delete cart items...");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i = new Intent(RestaurantsActivity.this, CartListActivity.class);
                startActivity(i);
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Oops...Menu's not available.");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();

    }

    public void backimage(View view) {
        finish();
    }


    public List<String> removeDuplicates(List<String> list) {

        List<String> newList = new ArrayList<String>();

        for (String element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }


}
