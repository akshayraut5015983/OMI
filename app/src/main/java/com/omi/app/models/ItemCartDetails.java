package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemCartDetails {
    @SerializedName("cart_id")
    @Expose
    public String cartId;
    @SerializedName("item_id")
    @Expose
    public String itemId;
    @SerializedName("item_name")
    @Expose
    public String itemName;
    @SerializedName("item_type1")
    @Expose
    public String itemType1;
    @SerializedName("item_type2")
    @Expose
    public String itemType2;
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
