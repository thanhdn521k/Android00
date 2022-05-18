package com.example.nhom18androidxedap.model;

import java.io.Serializable;
import java.util.Comparator;

public class Users implements Serializable {
    private int id;
    private String username;
    private String password;
    private String authority;
    private String fullname;
    private String gmail;

    public Users() {
    }

    public Users(int id, String username, String password, String authority, String fullname, String gmail) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.fullname = fullname;
        this.gmail = gmail;
    }

    public Users(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authority='" + authority + '\'' +
                ", fullname='" + fullname + '\'' +
                ", gmail='" + gmail + '\'' +
                '}';
    }
}
