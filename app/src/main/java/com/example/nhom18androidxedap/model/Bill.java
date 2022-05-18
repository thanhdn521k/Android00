package com.example.nhom18androidxedap.model;

import java.io.Serializable;
import java.util.List;

public class Bill implements Serializable {
    private int id;
    private String code;
    private Users users;
    private List<Bicycle> bicycleList;
    private Voucher voucher;
    private int quantity;
    private int total;
    private String start_at;
    private String end_at;
    private boolean status;

    public Bill() {
    }

    public Bill(int id, String code, Users users, List<Bicycle> bicycleList, Voucher voucher, int quantity, int total, String start_at, String end_at, boolean status) {
        this.id = id;
        this.code = code;
        this.users = users;
        this.bicycleList = bicycleList;
        this.voucher = voucher;
        this.quantity = quantity;
        this.total = total;
        this.start_at = start_at;
        this.end_at = end_at;
        this.status = status;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Bicycle> getBicycleList() {
        return bicycleList;
    }

    public void setBicycleList(List<Bicycle> bicycleList) {
        this.bicycleList = bicycleList;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuanity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
