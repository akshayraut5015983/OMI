package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrndingFoodItem {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("type1")
    @Expose
    public String type1;
    @SerializedName("type2")
    @Expose
    public String type2;
    @SerializedName("item_pic")
    @Expose
    public String itemPic;
}
