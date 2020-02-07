package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemData {
    @SerializedName("user_info")
    @Expose
    public ItemUserInfo userInfo;
    @SerializedName("rest_info")
    @Expose
    public ItemRestInfo restInfo;
    @SerializedName("order_info")
    @Expose
    public ItemOrderInfo orderInfo;
    @SerializedName("item_info")
    @Expose
    public List<ItemInfo> itemInfo = null;
}
