package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaundryListItems {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("dhobi_id")
    @Expose
    public String dhobiId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("order_no")
    @Expose
    public String orderNo;
    @SerializedName("cust_name")
    @Expose
    public String custName;
    @SerializedName("cust_mobile")
    @Expose
    public String custMobile;
    @SerializedName("cust_address")
    @Expose
    public String custAddress;
    @SerializedName("no_of_item")
    @Expose
    public String noOfItem;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("total_amount_cal")
    @Expose
    public String totalAmountCal;
    @SerializedName("gst_cal")
    @Expose
    public String gstCal;
    @SerializedName("comm_cal")
    @Expose
    public String commCal;
    @SerializedName("payment_mode")
    @Expose
    public String paymentMode;
    @SerializedName("order_date")
    @Expose
    public String orderDate;
    @SerializedName("payment_status")
    @Expose
    public String paymentStatus;
    @SerializedName("is_view")
    @Expose
    public String isView;
    @SerializedName("order_status")
    @Expose
    public String orderStatus;
    @SerializedName("deliveredTime")
    @Expose
    public String deliveredTime;
    @SerializedName("otp")
    @Expose
    public String otp;
    @SerializedName("dhobi_name")
    @Expose
    public String dhobiName;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("item_data")
    @Expose
    public List<ItemListLoundry> itemData = null;

}
