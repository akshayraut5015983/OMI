package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsDataLoundry {
    @SerializedName("user_info")
    @Expose
    public OrderUserInfoLoundry userInfo;
    @SerializedName("dhobi_info")
    @Expose
    public OrderDhobiInfoLoundery dhobiInfo;
    @SerializedName("order_info")
    @Expose
    public OrderInfoLoundry orderInfo;
    @SerializedName("item_info")
    @Expose
    public List<ItemInfoLoundry> itemInfo = null;
}
