package com.haz.mymusic.models;

/**
 * @author: hswplus
 * @date: 2022/4/14
 * @Description: 用户模型类 继承了RealmObject
 */


public class UserModel {

    private String phone;

    private String password;

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
}
