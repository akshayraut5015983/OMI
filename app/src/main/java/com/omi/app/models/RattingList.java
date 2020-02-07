package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RattingList {
    @SerializedName("rating_id")
    @Expose
    public String ratingId;
    @SerializedName("rating_count")
    @Expose
    public String ratingCount;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("merchant_id")
    @Expose
    public String merchantId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("review")
    @Expose
    public String review;
}
