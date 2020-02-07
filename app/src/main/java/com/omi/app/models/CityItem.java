package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityItem {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("city_name")
    @Expose
    public String cityName;
}
