package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantPriceItems {

    @SerializedName("wallet_usable")
    @Expose
    public Boolean walletUsable;
    @SerializedName("wallet_usable_amount")
    @Expose
    public double walletUsableAmount;
    @SerializedName("total_amount")
    @Expose
    public double totalAmount;
    @SerializedName("delivery_charges")
    @Expose
    public double deliveryCharges;
    @SerializedName("gst_charges")
    @Expose
    public double gstCharges;
    @SerializedName("final_amount")
    @Expose
    public double finalAmount;
}
