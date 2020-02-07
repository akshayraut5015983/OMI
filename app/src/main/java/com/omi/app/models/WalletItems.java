package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletItems {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("trans_amt")
    @Expose
    public String transAmt;
    @SerializedName("wallet_curr_amt")
    @Expose
    public String walletCurrAmt;
    @SerializedName("wallet_restaurant_amt")
    @Expose
    public String walletRestaurantAmt;
    @SerializedName("wallet_laundry_amt")
    @Expose
    public String walletLaundryAmt;
    @SerializedName("trans_date")
    @Expose
    public String transDate;
    @SerializedName("trans_desc")
    @Expose
    public String transDesc;
    @SerializedName("status")
    @Expose
    public String status;

}
