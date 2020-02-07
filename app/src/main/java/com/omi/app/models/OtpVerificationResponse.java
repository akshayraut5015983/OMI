package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerificationResponse {

    @SerializedName("flag")
    @Expose
    public Boolean flag;
    @SerializedName("msg")
    @Expose
    public String msg;

}
