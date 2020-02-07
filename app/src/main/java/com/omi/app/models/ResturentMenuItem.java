package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResturentMenuItem {
    @SerializedName("items")
    @Expose
    public List<RestaurantItemDetails> items = null;
    @SerializedName("type1")
    @Expose
    public String type1;
    @SerializedName("type2")
    @Expose
    public String type2;
}
