package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LounderyDetailsItems {

    @SerializedName("dhobi_id")
    @Expose
    public String dhobiId;
    @SerializedName("item_id")
    @Expose
    public String itemId;
    @SerializedName("item_name")
    @Expose
    public String itemName;
    @SerializedName("item_type_id")
    @Expose
    public String itemTypeId;
    @SerializedName("item_type_value")
    @Expose
    public String itemTypeValue;
    @SerializedName("item_status")
    @Expose
    public String itemStatus;
    @SerializedName("prc_iron")
    @Expose
    public String prcIron;
    @SerializedName("prc_wash")
    @Expose
    public String prcWash;
    @SerializedName("prc_iron_wash")
    @Expose
    public String prcIronWash;
    @SerializedName("prc_dry_clean")
    @Expose
    public String prcDryClean;
}
