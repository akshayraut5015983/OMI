package com.omi.app.models;

public class Laundry {

    String itemName, ser_type,price,qty;
            int clothes;

    public Laundry(String itemName, String ser_type, String price, String qty, int clothes) {
        this.itemName = itemName;
        this.ser_type = ser_type;
        this.price = price;
        this.qty = qty;
        this.clothes = clothes;
    }

    public String getItemName() {

        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSer_type() {
        return ser_type;
    }

    public void setSer_type(String ser_type) {
        this.ser_type = ser_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getClothes() {
        return clothes;
    }

    public void setClothes(int clothes) {
        this.clothes = clothes;
    }
}
