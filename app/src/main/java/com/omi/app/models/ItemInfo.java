package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemInfo {
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
    public Object type2;
    @SerializedName("qty")
    @Expose
    public String qty;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("item_total")
    @Expose
    public String itemTotal;
}
