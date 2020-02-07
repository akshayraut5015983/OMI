package com.omi.app.models;

import android.provider.ContactsContract;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListResponce {
    @SerializedName("userdata")
    @Expose
    public UserCartDetails userdata;
    @SerializedName("rest_info")
    @Expose
    public RestoCartDetails restInfo;
    @SerializedName("item_data")
    @Expose
    public List<ItemCartDetails> itemData = null;
    @SerializedName("flag")
    @Expose
    public Boolean flag;
    @SerializedName("price_details")
    @Expose
    public RestaurantPriceItems priceDetails;
}
