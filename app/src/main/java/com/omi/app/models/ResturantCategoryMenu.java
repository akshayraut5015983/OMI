package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResturantCategoryMenu {


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
    @SerializedName("type1")
    @Expose
    public String type1;
    @SerializedName("type2")
    @Expose
    public String type2;

    public ResturantCategoryMenu(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.trend = trend;
        this.trendImg = trendImg;
        this.type1 = type1;
        this.type2 = type2;
    }
}
