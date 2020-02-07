package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDataLoundry {
    @SerializedName("user_info")
    @Expose
    public CartUserInfoLoundry userInfo;
    @SerializedName("dhobi_info")
    @Expose
    public CartDhobiInfo dhobiInfo;
    @SerializedName("item_info")
    @Expose
    public List<CartItemLoundry> itemInfo = null;
}
