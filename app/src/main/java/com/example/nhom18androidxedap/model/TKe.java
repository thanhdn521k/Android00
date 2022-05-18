package com.example.nhom18androidxedap.model;

public class TKe {
//    private int bicycleID;
    private String month;
    private int total;
    private int quantity;

    public TKe() {
    }

    public TKe(String month, int total, int quantity) {
        this.month = month;
//        this.bicycleID = bicycleID;
        this.total = total;
        this.quantity = quantity;
    }

//    public int getBicycleID() {
//        return bicycleID;
//    }
//
//    public void setBicycleID(int bicycleID) {
//        this.bicycleID = bicycleID;
//    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
