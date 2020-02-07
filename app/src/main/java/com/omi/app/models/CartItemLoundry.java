package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItemLoundry {
    @SerializedName("cart_id")
    @Expose
    public String cartId;
    @SerializedName("item")
    @Expose
    public CartLoundryItem item;
    @SerializedName("qty")
    @Expose
    public String qty;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("item_total")
    @Expose
    public String itemTotal;//
    @SerializedName("item_opr_val")
    @Expose
    public String item_opr_val;
}
