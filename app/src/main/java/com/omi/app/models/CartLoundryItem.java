package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartLoundryItem {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("t_val")
    @Expose
    public String tVal;
    @SerializedName("photo")
    @Expose
    public String photo;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("t_value")
    @Expose
    public String tValue;

}
