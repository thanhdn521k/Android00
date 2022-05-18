package com.example.nhom18androidxedap.model;

import java.io.Serializable;
import java.util.Comparator;

public class Voucher implements Serializable {
    private int id;
    private String code;
    private int discount;
    private int min_bill;
    private String start_at;
    private String end_at;

    public Voucher() {
    }

    public Voucher(int id, String code, int discount, int min_bill, String start_at, String end_at) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.min_bill = min_bill;
        this.start_at = start_at;
        this.end_at = end_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getMin_bill() {
        return min_bill;
    }

    public void setMin_bill(int min_bill) {
        this.min_bill = min_bill;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }
}
