package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemLoundryInfo {
    @SerializedName("0")
    @Expose
    public ForLoundry _0;
    @SerializedName("qty")
    @Expose
    public String qty;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("item_opr_val")
    @Expose
    public String itemOprVal;
    @SerializedName("item_total")
    @Expose
    public String itemTotal;

}
