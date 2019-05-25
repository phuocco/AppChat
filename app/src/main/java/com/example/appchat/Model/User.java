package com.example.appchat.Model;

public class User {
    public String phoneNumber;
    public String name;
    public String matKhau;
    public String avatar;
    public String keyUser;
    public User() {
    }

    public User(String phoneNumber, String name, String matKhau, String avatar) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.matKhau = matKhau;
        this.avatar = avatar;
    }

    public User(String phoneNumber, String name, String matKhau, String avatar, String keyUser) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.matKhau = matKhau;
        this.avatar = avatar;
        this.keyUser = keyUser;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public User(String phoneNumber, String matKhau) {
        this.phoneNumber = phoneNumber;
        this.matKhau = matKhau;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
