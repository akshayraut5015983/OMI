package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemInfoLoundry {
    @SerializedName("0")
    @Expose
    public OrderLoundryOther _0;
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
