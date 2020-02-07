package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantItemDetails {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("trend")
    @Expose
    public String trend;
    @SerializedName("trend_img")
    @Expose
    public String trendImg;
    @SerializedName("photo")
    @Expose
    public String photo;

    @SerializedName("type1")
    @Expose
    public String type1;
}
