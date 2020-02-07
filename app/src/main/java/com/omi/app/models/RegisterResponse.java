package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("flag")
    @Expose
    public Boolean flag;
    @SerializedName("data")
    @Expose
    public String data;
}
