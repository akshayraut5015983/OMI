package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderUserInfoLoundry {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile_no")
    @Expose
    public String mobileNo;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("device_id")
    @Expose
    public String deviceId;
    @SerializedName("fcm_token")
    @Expose
    public String fcmToken;

}
