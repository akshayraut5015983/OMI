package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {

    @SerializedName("flag")
    @Expose
    public Boolean flag;
    @SerializedName("data")
    @Expose
    public List<OrderListItems> data = null;

}
