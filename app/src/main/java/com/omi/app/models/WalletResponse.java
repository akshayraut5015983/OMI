package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletResponse {
    @SerializedName("wallet_history")
    @Expose
    public List<WalletItems> walletHistory = null;
}
