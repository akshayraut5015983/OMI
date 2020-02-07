package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceItem {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("reclycler_text")
    @Expose
    public String reclyclerText;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("image_banner")
    @Expose
    public String imageBanner;
    @SerializedName("image_icon")
    @Expose
    public String imageIcon;
    @SerializedName("parent_id")
    @Expose
    public String parentId;
    @SerializedName("status")
    @Expose
    public String status;
}
