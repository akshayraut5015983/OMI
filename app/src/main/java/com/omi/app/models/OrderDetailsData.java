package com.omi.app.models;

import com.google.firebase.auth.UserInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsData {
    @SerializedName("user_info")
    @Expose
    public OrderUserInfo userInfo;
    @SerializedName("rest_info")
    @Expose
    public OrderRestInfo restInfo;
    @SerializedName("order_info")
    @Expose
    public OrderInfo orderInfo;
    @SerializedName("item_info")
    @Expose
    public List<OrderDetailsItem> itemInfo = null;
}
