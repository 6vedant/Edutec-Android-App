package com.edutech.app.others;

/**
 * Created by vedant on 1/14/2018.
 */

public class UserDetails {
    public String email,phone,name,imei,date,photo_url;


    public UserDetails(String email, String phone, String name, String imei, String date, String photo_url) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.imei = imei;
        this.date = date;
        this.photo_url = photo_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
