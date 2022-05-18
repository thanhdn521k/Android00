package com.example.nhom18androidxedap.model;

import java.io.Serializable;
import java.util.Comparator;

public class Notify implements Serializable {
    private int id;
    private String title;
    private String detail;
    private String time;

    public Notify() {
    }

    public Notify(int id, String title, String detail, String time) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
