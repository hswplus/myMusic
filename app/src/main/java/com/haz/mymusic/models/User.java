package com.haz.mymusic.models;

/**
 * @author: hswplus
 * @date: 2022/4/14
 * @Description:
 */


public class User {

    public String phone;

    public String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
