package com.omi.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemListLoundry implements Parcelable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("dhobi_id")
    @Expose
    public String dhobiId;
    @SerializedName("item_id")
    @Expose
    public String itemId;
    @SerializedName("item_opr")
    @Expose
    public String itemOpr;
    @SerializedName("qty")
    @Expose
    public String qty;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("item_total")
    @Expose
    public String itemTotal;
    @SerializedName("is_order")
    @Expose
    public String isOrder;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("cart_time")
    @Expose
    public String cartTime;
    @SerializedName("type_value")
    @Expose
    public String typeValue;
    @SerializedName("item_name")
    @Expose
    public String itemName;
    @SerializedName("opr_value")
    @Expose
    public String oprValue;

    protected ItemListLoundry(Parcel in) {
        id = in.readString();
        userId = in.readString();
        dhobiId = in.readString();
        itemId = in.readString();
        itemOpr = in.readString();
        qty = in.readString();
        price = in.readString();
        itemTotal = in.readString();
        isOrder = in.readString();
        orderId = in.readString();
        cartTime = in.readString();
        typeValue = in.readString();
        itemName = in.readString();
        oprValue = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(dhobiId);
        dest.writeString(itemId);
        dest.writeString(itemOpr);
        dest.writeString(price);
        dest.writeString(qty);
        dest.writeString(itemTotal);
        dest.writeString(isOrder);
        dest.writeString(orderId);
        dest.writeString(cartTime);
        dest.writeString(typeValue);
        dest.writeString(itemName);
        dest.writeString(oprValue);

    }

    public static final Creator<ItemListLoundry> CREATOR = new Creator<ItemListLoundry>() {
        @Override
        public ItemListLoundry createFromParcel(Parcel in) {
            return new ItemListLoundry(in);
        }

        @Override
        public ItemListLoundry[] newArray(int size) {
            return new ItemListLoundry[size];
        }
    };
}
