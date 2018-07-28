package com.edutech.app.others;

/**
 * Created by vedant on 1/18/2018.
 */

public class KeyActivationDetails {
    public String key,phone,date,imei,pack;

    public KeyActivationDetails(String key, String phone, String date, String imei, String pack) {
        this.key = key;
        this.phone = phone;
        this.date = date;
        this.imei = imei;
        this.pack = pack;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }
}
