package com.omi.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemListRest implements Parcelable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("res_id")
    @Expose
    public String resId;
    @SerializedName("item_id")
    @Expose
    public String itemId;
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
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("type1")
    @Expose
    public String type1;
    @SerializedName("type2")
    @Expose
    public String type2;

    protected ItemListRest(Parcel in) {
        id = in.readString();
        userId = in.readString();
        resId = in.readString();
        itemId = in.readString();
        qty = in.readString();
        price = in.readString();
        itemTotal = in.readString();
        isOrder = in.readString();
        orderId = in.readString();
        cartTime = in.readString();
        name = in.readString();
        type1 = in.readString();
        type2 = in.readString();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(resId);
        dest.writeString(itemId);
        dest.writeString(price);
        dest.writeString(qty);
        dest.writeString(itemTotal);
        dest.writeString(isOrder);
        dest.writeString(orderId);
        dest.writeString(cartTime);
        dest.writeString(name);
        dest.writeString(type1);
        dest.writeString(type2);

    }
    public static final Creator<ItemListRest> CREATOR = new Creator<ItemListRest>() {
        @Override
        public ItemListRest createFromParcel(Parcel in) {
            return new ItemListRest(in);
        }

        @Override
        public ItemListRest[] newArray(int size) {
            return new ItemListRest[size];
        }
    };
}
