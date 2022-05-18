package com.example.nhom18androidxedap.model;

import java.io.Serializable;

public class Bicycle implements Serializable {
    private int id;
    private String code;
    private String condition;
    private String note;
    private boolean status;

    public Bicycle() {
    }

    public Bicycle(int id, String code, String condition, String note, boolean status) {
        this.id = id;
        this.code = code;
        this.condition = condition;
        this.note = note;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", condition='" + condition + '\'' +
                ", note='" + note + '\'' +
                ", status=" + status +
                '}';
    }
}
