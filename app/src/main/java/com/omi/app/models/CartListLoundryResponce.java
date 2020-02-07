package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartListLoundryResponce {
    @SerializedName("flag")
    @Expose
    public Boolean flag;
    @SerializedName("data")
    @Expose
    public CartDataLoundry data;
    @SerializedName("price_details")
    @Expose
    public RestaurantPriceItems priceDetails;
}
