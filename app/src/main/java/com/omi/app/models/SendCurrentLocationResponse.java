package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendCurrentLocationResponse {
    @SerializedName("flag")
    @Expose
    public Boolean success;
    @SerializedName("data")
    @Expose
    public String message;

}
