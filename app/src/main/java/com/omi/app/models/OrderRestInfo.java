package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRestInfo {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("locality")
    @Expose
    public Object locality;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("logitude")
    @Expose
    public String logitude;
    @SerializedName("hr_of_operation")
    @Expose
    public String hrOfOperation;
    @SerializedName("other_facility")
    @Expose
    public String otherFacility;
    @SerializedName("website_email")
    @Expose
    public String websiteEmail;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("photo")
    @Expose
    public String photo;
    @SerializedName("m_service")
    @Expose
    public String mService;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("f_id")
    @Expose
    public String fId;
    @SerializedName("march_id")
    @Expose
    public String marchId;
    @SerializedName("package")
    @Expose
    public String _package;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("close_day")
    @Expose
    public String closeDay;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("best_saller")
    @Expose
    public String bestSaller;
    @SerializedName("gst_no")
    @Expose
    public String gstNo;
    @SerializedName("commision")
    @Expose
    public String commision;
    @SerializedName("close_by_merch")
    @Expose
    public String closeByMerch;
}
