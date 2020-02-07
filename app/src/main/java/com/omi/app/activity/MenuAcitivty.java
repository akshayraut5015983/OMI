package com.omi.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.utils.ErrorObject;
import com.omi.app.models.LoginItems;
import com.omi.app.models.OrderHistoryResponce;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import static com.omi.app.utils.Constants.AppConst.IS_LOGIN;
import static com.omi.app.utils.Constants.AppConst.LOGIN_ITEMS;

public class MenuAcitivty extends AppCompatActivity implements RetrofitListener, NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    TextView textView;
    private ApiServiceProvider apiServiceProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_menu);
        apiServiceProvider = ApiServiceProvider.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        textView = (TextView) findViewById(R.id.txt);

        LoginItems loginItems = (LoginItems) SharePreferenceUtility.getPreferences(this, LOGIN_ITEMS, SharePreferenceUtility.LOGIN_OBJECT);

        if (loginItems != null) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_my_profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_changepassword).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_gallery).setVisible(true);
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            navigationView.getMenu().findItem(R.id.contact).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_home).setVisible(true);

        } else {
            navigationView.getMenu().findItem(R.id.login).setVisible(true);
            navigationView.getMenu().findItem(R.id.contact).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_my_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_home).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_changepassword).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_gallery).setVisible(false);
        }

        String src = "India’s first multi service APP. which has tried to give a wheel to the entire rural market.\n" + "<br>" +
                "Search, Hotel, Restaurants, Travel, Doctor, Accessories, Day-to-Day needs, Repair Service, Home Service, Wedding, Entertainment and much more.\n" +
                "Save Time and Money with the OMI App!\n" + "<br>" +
                "Not only Search, now you can also transact on OMI. You can do online shopping, get instant quotes and buy products from shops around you. You can book a table at your favourite restaurant, book bus, order food online from your favourite restaurant near you, book an appointment with your doctor near you, book a hotel room for your travel, book a car for your long vacation drive and do much more in just a few clicks.\n" +
                "Search Anything, Anytime, Anywhere using OMI. You get Phone Numbers, Addresses, Best Deals, Latest Reviews and Ratings for all listed and your required businesses instantly.\n" + "<br>" +
                "OMI app works as a one stop solution for all your daily needs - whether it is information about, Restaurants next door, Hotels, Doctors, Electronics Stores, Electrician, Repair service, Dhobi wale, Civil construction work, Education, Loan, Plumber, Real Estate or as simple as the nearest Pan wale, OMI has it all.\n" +
                "India’s easiest service provider launching with an idea which promote to make digital India and it is coming with 50+ services in a single application.\n" +
                "You can enjoy some of the amazing features listed below, when you download the OMI App and same\n" + "<br>" +
                "Is the reason to download this app.\n" + "<br>" +
                "<b>" + "Home Services\n" + "</b>" + "<br>" +

                "·         Search your nearest Home Services\n" + "<br>" +
                "\n" +
                "·         Book Appointment Plumber, Electrician, Laundry, Painters, Maid, Drivers and many more\n" + "<br>" +
                "\n" +
                "<b>" + "Restaurant\n" + "</b>" + "<br>" +
                "\n" +
                "·         Book your food order from your favourite restaurant\n" + "<br>" +
                "\n" +
                "·         search your favourite restaurant pick your food  place order\n" + "<br>" +
                "\n" +
                "·         Order food on the go\n" + "<br>" +
                "\n" +
                "·         Quickly find restaurant info such as phone numbers, addresses and driving directions, and hours of operation\n" + "<br>" +
                "\n" +
                "·         Showcases full menu with rates of restaurants in your vicinity\n" + "<br>" +
                "\n" +
                "·         Easily Add dishes to your order through a single click\n" + "<br>" +
                "<b>" + "Laundry\n" + "</b>" + "<br>" +
                "\n" +
                "·         First door to door dhobiwala services in your area.\n" + "<br>" +
                "\n" +
                "·         Choose your nearest dhobiwala shop book your order.\n" + "<br>" +
                "\n" +
                "·         Set pickup and delivery time and enjoy easiest dhobiwala service.\n" + "<br>" +
                "\n" + "\n" + "<b>" + "Coming Soon....\n" + "</b>" + "<br>" + "<br>" +
                "<b>" + "Medical Services\n" + "</b>" + "<br>" +
                "·         Doctors, Hospital, Ambulance and Pathology\n" +
                "·         search your required medicals services and book appointment\n" +
                "·         easy to booked appointment\n" + "<br>" +

                "<b>" + "Hotel\n" + "</b>" + "<br>" +
                "·         Save time on Searching Hotel, Lodge and Guest House.\n" + "<br>" +
                "·         You can search your hotel lodge and Guest house\n" + "<br>" +
                "·         Book a Hotel anywhere in India in just a few clicks\n" + "<br>" +
                "·         Quick and Easy Hotel Bookings\n" + "<br>" +
                "·         Auto detection of your current location with the help of GPS\n" + "<br>" +
                "·         Close proximity search through NEAR ME feature\n" + "<br>" +
                "·         Provides Business Information in depth\n" + "<br>" +
                "\n" +
                "·         Allows you the option of paying at the hotel\n" + "<br>" +

                "<b>" + "Stores\n" + "</b>" + "<br>" +
                "·         E Commerce, book your grocery from your favourite store\n" + "<br>" +
                "·         Book your required item from online stores near you.\n" + "<br>" +
                "·         Search your stores, book order and\n" + "<br>" +
                "·         Enjoy the India’s first easiest online store services from Grocery Stores, Cosmetic Stores, Sweets and Bakery, Hardware stores, Electronics, medical stores, Stationary Stores, Mobile Stores, Auto parts, Jewellery and Furniture Stores.\n" + "<br>" +
                "<b>" + "Salon and Parlour\n" + "</b>" + "<br>" +
                "·         Book your appointment in your convenient time from office and home.\n" + "<br>" +
                "·         Find salon and Parlor near you\n" + "<br>" +
                "·         Easy to book your appointment.\n" + "<br>" +
                "·         You can get a categories salon with gold, silver and platinum rating by OMI\n" + "<br>" +
                "<b>" + "Buy and Sell\n" + "</b>" + "<br>" +
                "·         Sell and Buy your accessories, books, cloths, mobile, and product you want sell and buy.\n" + "<br>" +
                "·         Post your ad and experience the India’s First Easiest service.\n" + "<br>" +
                "·         Broker service\n" + "<br>" +
                "·         Easy to find your rented Home\n" + "<br>" +
                "<b>" + "Travel\n" + "</b>" + "<br>" +
                "·         Fastest booking process in just few touch, you can book Flight Ticket, Train Ticket, Bus Ticket, traveller And Sumo, you can also make contact with your nearest travel agent.\n" + "<br>" +
                "<b>" + "Repair and Service\n" + "</b>" + "<br>" +
                "\n" +
                "·         Millions of Repair centre are listed on OMI.\n" + "<br>" +
                "\n" +
                "·         Find repair centre filtered by location, review and comment.\n" + "<br>" +
                "\n" +
                "·         Book your online Services from car and bike repair services, refrigerator, AC, television, computer and laptop, Mobile and Tabs, Washing machine, Water Purifier, Water Pump.\n" + "<br>" +

                "<b>" + "Education\n" + "</b>" + "<br>" +
                "\n" +
                "·         Search best school, college, coaching institute and tutor.\n" + "<br>" +
                "\n" +
                "·         Search your best education system in your area book appointment and enjoy the best education in your area.\n" + "<br>" +


                "<b>" + "Wedding\n" + "</b>" + "<br>" +
                "\n" +
                "·         It is an important and first ever services across India. through  wedding services you can book your all wedding services like\n" + "<br>" +
                "\n" +
                "·         banquet hall, Caterers, Decoration, Dhol and Bhangra, Photographers, Wedding Cards, bridal Makeup, pandit Ji, Fireworks, DJ on Hire.\n" + "<br>" +

                "Other services like Bike Booking, Traveller  Booking, Sumo Booking ,Order Food , Book A Table, Grocery Shopping, Booking a Car for long derive, Book A Doctor Appointment, Order Flowers, Order Medicine, Repair Services ,Home Services, Maintenance and can also be availed of in few clicks.\n" + "<br>" +

                "<b>" + "Other Valuable Features:-\n" + "</b>" + "<br>" +

                "·         Get unbelievably BEST DEALS as several vendors compete for your requirement\n" + "<br>" +
                "\n" +
                "·         Add businesses to your list of Favourites for ease of use\n" + "<br>" +
                "\n" +
                "·         Add your business listing FREE of charge\n" + "<br>" +
                "\n" +
                "·         Share OMI App with your friends and Win Prizes\n" + "<br>" +
                "\n" +
                "·         Donate your unused item and help to needy\n" + "<br>" +

                "So what are you waiting for download India’s first easiest all service app now - Authorised you to make a suggested choice for all your service need. Go ahead and download the OMI APP today and get your Smartphone to do much more.\n" + "<br>" +

                "Please note this release is only for our channel partner and PAID Merchant.\n" + "<br>" +
                "\n" +
                "We will update the app with new features and services from regular maintenance. Stay in touch for best services and updates.\n" + "<br>" + "<br>" +

                " For more you can check WWW.OMIONLINE.IN\n" + "<br>" +
                "\n" + "<br>" +
                "We are happy to hear from you.\n" + "<br>" +
                "<b>" + "support@omionline.in" + "<br>" +
                "info@omionline.in" + "</b>";
        textView.setText(Html.fromHtml(src));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            Intent ik = new Intent(MenuAcitivty.this, HomeActivity.class);
            startActivity(ik);
        } else if (id == R.id.nav_login) {
            Intent ik = new Intent(MenuAcitivty.this, AboutActivity.class);
            startActivity(ik);
        } else if (id == R.id.contact) {
            Intent ik = new Intent(MenuAcitivty.this, ContactUS.class);
            startActivity(ik);
        } else if (id == R.id.login) {
            Intent ik = new Intent(MenuAcitivty.this, LoginActivity.class);
            startActivity(ik);
        } else if (id == R.id.nav_gallery) {
            android.support.v7.app.AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(this).

                            setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharePreferenceUtility.saveBooleanPreferences(MenuAcitivty.this, IS_LOGIN, false);
                                            SharePreferenceUtility.saveObjectPreferences(MenuAcitivty.this, LOGIN_ITEMS, null);
                                            Intent ik = new Intent(MenuAcitivty.this, LoginActivity.class);
                                            startActivity(ik);
                                        }
                                    }
                            ).setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.setTitle("Do you want to Logout..?");
            android.support.v7.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (id == R.id.nav_changepassword) {
            Intent ik = new Intent(MenuAcitivty.this, ForgotPassword.class);
            startActivity(ik);

        } else if (id == R.id.nav_my_profile) {
            Intent ik = new Intent(MenuAcitivty.this, ProfileAcivity.class);
            startActivity(ik);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof OrderHistoryResponce) {
            OrderHistoryResponce cardResponce = (OrderHistoryResponce) responseBody;
            if (cardResponce.flag) {
                Toast.makeText(this, "Order History Find Successfully", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        Toast.makeText(this, errorObject.getDeveloperMessage(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
