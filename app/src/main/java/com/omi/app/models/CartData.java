package com.omi.app.models;

import com.google.firebase.auth.UserInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartData {
    @SerializedName("user_info")
    @Expose
    public CartUserInfo userInfo;
    @SerializedName("rest_info")
    @Expose
    public CartRestInfo restInfo;
    @SerializedName("item_info")
    @Expose
    public List<CartItem> itemInfo = null;
}
