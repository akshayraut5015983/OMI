package com.omi.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoundryTypeItem {
    @SerializedName("dhobi_id")
    @Expose
    public String dhobiId;
    @SerializedName("item_pic")
    @Expose
    public String itemPic;
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
    @SerializedName("price")
    @Expose
    public String price;
}
