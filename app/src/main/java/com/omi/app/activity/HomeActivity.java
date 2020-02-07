package com.omi.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.adapter.BannerListAdapter;
import com.omi.app.adapter.BannerPagerAdapter;
import com.omi.app.adapter.BestSellerAdapter;
import com.omi.app.adapter.RecyclerViewAdapter;
import com.omi.app.adapter.ServiceListAdapter;
import com.omi.app.adapter.TrendingFoodAdapter;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.BestSellerItem;
import com.omi.app.models.BestSellerResponce;
import com.omi.app.models.CartListResponce;
import com.omi.app.models.CityItem;
import com.omi.app.models.CityResponce;
import com.omi.app.models.FcmSendResponse;
import com.omi.app.models.LoginItems;
import com.omi.app.models.OrderDetailsResponce;
import com.omi.app.models.OrderHistoryResponce;
import com.omi.app.models.OrderPlaceResponce;
import com.omi.app.models.ServiceItem;
import com.omi.app.models.ServiceResponce;
import com.omi.app.models.TrendingFoodResponce;
import com.omi.app.models.TrndingFoodItem;
import com.omi.app.utils.BadgeDrawable;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.omi.app.utils.Constants.AppConst.CART_COUNT;
import static com.omi.app.utils.Constants.AppConst.CITY;
import static com.omi.app.utils.Constants.AppConst.CITY_LOCATION;
import static com.omi.app.utils.Constants.AppConst.FCM_TOKEN;
import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;
import static com.omi.app.utils.Constants.AppConst.LONGI;
import static com.omi.app.utils.Constants.AppConst.USER_LOCATION;

public class HomeActivity extends AppCompatActivity implements RetrofitListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private ApiServiceProvider apiServiceProvider;
    private RecyclerView mRviewServiceList, mRviewBestSell, mRecyclerViewTrending, mRecyclerViewBanner;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    private LinearLayoutManager HorizontalLayout;
    private View ChildView;
    BottomNavigationView navigationView;
    private int RecyclerViewItemPosition;
    private TextView tv1, tv2, tv_no_trending_service, tv_no_trending_food_service;
    private ImageView imageViewSerch;
    private HorizontalScrollView horizontalScrollView, horizontalScrollViewB;
    private TextView tvLocation;
    private Intent intent;
    private Dialog dialog;
    private LoginItems loginItems;
    private CityItem cityItem;
    private TextView mTvCount;
    private FrameLayout mFrameLayout;
    private String longi;
    private String lat;
    private List<ServiceItem> serviceItems;
    Handler handler;
    CartListResponce cartList;
    private LinearLayout layoutTrend, layoutBestSell, layoutComing;
    AdapterViewFlipper viewFlipper;
    private ViewPager viewPager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        tvLocation = findViewById(R.id.tv_location);
        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(HomeActivity.this);
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        mRecyclerViewBanner = findViewById(R.id.recyclerview1);
        mRviewServiceList = findViewById(R.id.recyclerview2);
        mRecyclerViewTrending = findViewById(R.id.recyclerview3);
        mRviewBestSell = findViewById(R.id.recyclerview4);
        imageViewSerch = findViewById(R.id.serchView);
        tv1 = (TextView) findViewById(R.id.tredingfood);
        searchView = findViewById(R.id.ed_serch);
        mTvCount = (TextView) findViewById(R.id.cart_badge);
        mFrameLayout = findViewById(R.id.fr_count);
        layoutBestSell = findViewById(R.id.layoutbest);
        layoutComing = findViewById(R.id.comming);
        layoutTrend = findViewById(R.id.layouttrend);

        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
            }
        });

        layoutBestSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "No best seller available near you", Toast.LENGTH_SHORT).show();
            }
        });

        layoutTrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "No trending food available near you", Toast.LENGTH_SHORT).show();

            }
        });

        layoutComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Thanks for your patience. Service is coming soon in your location", Toast.LENGTH_SHORT).show();

            }
        });

        lat = (String) SharePreferenceUtility.getPreferences(HomeActivity.this, LAT, SharePreferenceUtility.PREFTYPE_STRING);
        longi = (String) SharePreferenceUtility.getPreferences(HomeActivity.this, LONGI, SharePreferenceUtility.PREFTYPE_STRING);


        horizontalScrollView = findViewById(R.id.hora);
        horizontalScrollViewB = findViewById(R.id.horb);
        tv1.setText(Html.fromHtml("<u>Trending Food</u> "));
        tv2 = (TextView) findViewById(R.id.tredingplace);
        tv2.setText(Html.fromHtml("<u>Best Seller</u> "));
        TextView tvComing = findViewById(R.id.tv_coming);
        tvComing.setText(Html.fromHtml("<u>Coming Soon</u>"));
        TextView seeall = findViewById(R.id.seeall);
        seeall.setText(Html.fromHtml("<u>See All</u>"));
        loginItems = (LoginItems) SharePreferenceUtility.getPreferences(this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        imageViewSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLocation.setVisibility(View.GONE);
                imageViewSerch.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if(serviceItems.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(HomeActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        onClickMethod();

        String token = (String) SharePreferenceUtility.getPreferences(this, FCM_TOKEN, SharePreferenceUtility.PREFTYPE_STRING);
        if (loginItems != null) {
            apiServiceProvider.getRequestSendFcm(token, loginItems.id, HomeActivity.this);
//            apiServiceProvider.getRequestCartList(loginItems.id, HomeActivity.this);
        }


        String count = (String) SharePreferenceUtility.getPreferences(this, CART_COUNT, SharePreferenceUtility.PREFTYPE_STRING);
//        if (count.equals("cart_count")||count.equals("")||count.equals(String.valueOf(0))) {
        if (count.isEmpty() || count.equals(String.valueOf(0))) {
            mTvCount.setVisibility(View.GONE);
        }
        mTvCount.setText(String.valueOf(count));
    }

    private void onClickMethod() {

        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            if (getIntent().hasExtra("key")) {
                value = extras.getString("key");
            } else {
                value = (String) SharePreferenceUtility.getPreferences(HomeActivity.this, USER_LOCATION, SharePreferenceUtility.PREFTYPE_STRING);
            }

        } else {
            value = (String) SharePreferenceUtility.getPreferences(HomeActivity.this, USER_LOCATION, SharePreferenceUtility.PREFTYPE_STRING);
        }
        tvLocation.setText(value);

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Do you want to set your location");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(HomeActivity.this, DeliverLocationActivity.class);
                        intent.putExtra("from_home_page", true);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                Dialog dialog = builder.create();
                dialog.show();

            }
        });

        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewBanner.setLayoutManager(horizontalLayoutManager2);
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRviewServiceList.setLayoutManager(horizontalLayoutManager1);
        LinearLayoutManager horizontalf = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTrending.setLayoutManager(horizontalf);
        LinearLayoutManager horizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRviewBestSell.setLayoutManager(horizontal);

        if (NetworkUtils.isNetworkConnected(HomeActivity.this)) {
            apiServiceProvider.getRequestServiceList(HomeActivity.this);
//            apiServiceProvider.getRequestCityList(HomeActivity.this);
            apiServiceProvider.getTrendingFood(lat, longi, "0", HomeActivity.this);
            apiServiceProvider.getRequestBestSeller(lat, longi, "0", HomeActivity.this);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

        }

        dialog.show();
    }

    class RemindTask extends TimerTask {
        int no_of_pages;
        ViewPager viewPager;
        int page = 0;

        public RemindTask(int no_of_pages, ViewPager viewPager) {
            this.no_of_pages = no_of_pages;
            this.viewPager = viewPager;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page > no_of_pages) {
                        viewPager.setCurrentItem(0);
                        page = 0;

                    } else {
                        viewPager.setCurrentItem(page++);
                    }
                }
            });
        }
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        dialog.dismiss();
        if (responseBody instanceof ServiceResponce) {
            ServiceResponce serviceResponce = (ServiceResponce) responseBody;
            if (serviceResponce.flag) {
                serviceItems = ((ServiceResponce) responseBody).data;
                final BannerListAdapter listAdapter = new BannerListAdapter(serviceItems, HomeActivity.this);
                mRecyclerViewBanner.setAdapter(listAdapter);

                BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(HomeActivity.this, serviceItems);
                viewPager.setAdapter(bannerPagerAdapter);
                Timer timer = new Timer();
                timer.schedule(new RemindTask(serviceItems.size(), viewPager), 500, 1000);

                ServiceListAdapter banner = new ServiceListAdapter(serviceItems, HomeActivity.this);
                mRviewServiceList.setAdapter(banner);
            } else {
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof BestSellerResponce) {
            BestSellerResponce cardResponce = (BestSellerResponce) responseBody;
            if (cardResponce.flag) {
                mRviewBestSell.setVisibility(View.VISIBLE);
                horizontalScrollViewB.setVisibility(View.GONE);
                List<BestSellerItem> bestSellerItems = ((BestSellerResponce) responseBody).data;

                BestSellerAdapter bestSellerAdapter = new BestSellerAdapter(bestSellerItems, HomeActivity.this);
                mRviewBestSell.setAdapter(bestSellerAdapter);
            } else {
                tv2.setVisibility(View.GONE);
//                Toast.makeText(this, "Response Fail", Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof CartListResponce) {
            cartList = (CartListResponce) responseBody;

        } else if (responseBody instanceof OrderDetailsResponce) {
            OrderDetailsResponce cardResponce = (OrderDetailsResponce) responseBody;
            if (cardResponce.flag) {
                Toast.makeText(this, "Order Details Find Successfully", Toast.LENGTH_LONG).show();
            }
        } else if (responseBody instanceof OrderHistoryResponce) {
            OrderHistoryResponce cardResponce = (OrderHistoryResponce) responseBody;
            if (cardResponce.flag) {
            }
        } else if (responseBody instanceof TrendingFoodResponce) {
            TrendingFoodResponce cardResponce = (TrendingFoodResponce) responseBody;
            if (cardResponce.flag) {
                mRecyclerViewTrending.setVisibility(View.VISIBLE);
                horizontalScrollView.setVisibility(View.GONE);
                List<TrndingFoodItem> bestSellerItems = ((TrendingFoodResponce) responseBody).data;
                TrendingFoodAdapter bestSellerAdapter = new TrendingFoodAdapter(bestSellerItems, HomeActivity.this);
                mRecyclerViewTrending.setAdapter(bestSellerAdapter);
            } else {
//                Toast.makeText(this, "Response Fail", Toast.LENGTH_SHORT).show();
            }
        } else if (responseBody instanceof FcmSendResponse) {
            FcmSendResponse fcmSendResponse = (FcmSendResponse) responseBody;
            if (fcmSendResponse.success) {
                // data send
            }
        }
        if (responseBody instanceof CityResponce) {
            List<CityItem> cityItems = ((CityResponce) responseBody).data;
            String city_name = (String) SharePreferenceUtility.getPreferences(HomeActivity.this, CITY, SharePreferenceUtility.PREFTYPE_STRING);

            if (getIntent().getExtras() != null) {

                CityItem city_data = (CityItem) SharePreferenceUtility.getPreferences(HomeActivity.this, CITY_LOCATION, SharePreferenceUtility.LOCATION_OBJECT);
                tvLocation.setText(city_data.cityName);
                apiServiceProvider.getTrendingFood(lat, longi, "0", HomeActivity.this);
                apiServiceProvider.getRequestBestSeller(lat, longi, "0", HomeActivity.this);

            } else {
                String[] city_data = city_name.split(",");
                for (int i = 0; i < cityItems.size(); i++) {
                    if (city_data.length == 1) {
                        if (city_name.contains(cityItems.get(i).cityName)) {
                            cityItem = cityItems.get(i);
                            SharePreferenceUtility.saveCityPreferences(HomeActivity.this, CITY_LOCATION, cityItem);
//                            tvLocation.setText(city_name);
                            apiServiceProvider.getTrendingFood(lat, longi, cityItem.id, HomeActivity.this);
                            apiServiceProvider.getRequestBestSeller(lat, longi, cityItem.id, HomeActivity.this);
                        }
                    } else if (city_data[4].contains(cityItems.get(i).cityName)) {
                        cityItem = cityItems.get(i);
                        SharePreferenceUtility.saveCityPreferences(HomeActivity.this, CITY_LOCATION, cityItem);
//                        tvLocation.setText(city_data[4]);
                        apiServiceProvider.getTrendingFood(lat, longi, cityItem.id, HomeActivity.this);
                        apiServiceProvider.getRequestBestSeller(lat, longi, cityItem.id, HomeActivity.this);
                    }
                }
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        dialog.dismiss();
//        Toast.makeText(this, errorObject.getDeveloperMessage() + " Fail", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                LoginItems loginItems1 = (LoginItems) SharePreferenceUtility.getPreferences(HomeActivity.this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);
                if (loginItems1 == null) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));

                }

                return true;
            case R.id.navigation_info:
                Intent ik = new Intent(HomeActivity.this, MenuAcitivty.class);
                startActivity(ik);
                return true;

            case R.id.navigation_scoial:


                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.social_layout);
                dialog.setTitle("Select at-least one category");

                Button dialogButton = dialog.findViewById(R.id.cancel);
                ImageView facebook = dialog.findViewById(R.id.facebook);
                ImageView insta = dialog.findViewById(R.id.insta);
                ImageView twitter = dialog.findViewById(R.id.twit);
                ImageView gplus = dialog.findViewById(R.id.gplus);

                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://www.facebook.com/omiapp/"));
                        startActivity(intent);
                    }
                });

                insta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://www.instagram.com/omiapp/?hl=en"));
                        startActivity(intent);
                    }
                });

                twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://twitter.com/app_omi"));
                        startActivity(intent);
                    }
                });

                gplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://plus.google.com/u/0/106830144015748727106"));
                        startActivity(intent);
                    }
                });
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();


                return true;
            case R.id.navigation_cart:
                if (loginItems != null) {
                    startActivity(new Intent(HomeActivity.this, OrderActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                }
                return true;
            case R.id.navigation_wallet:
                if (loginItems != null) {
                    startActivity(new Intent(HomeActivity.this, WalletActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                }

                return true;
        }

        return false;
    }


    public void onBackPressed() {

        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(this).

                        setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent a = new Intent(Intent.ACTION_MAIN);
                                        a.addCategory(Intent.CATEGORY_HOME);
                                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(a);
                                    }
                                }
                        ).setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setTitle("Do you want to Close..?");
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void seeAll(View view) {

        Intent i = new Intent(HomeActivity.this, ResturentListActivity.class);
        i.putExtra("city_id", "0");
        startActivity(i);

    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

}
