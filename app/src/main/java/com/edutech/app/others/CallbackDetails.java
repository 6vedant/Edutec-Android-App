package com.edutech.app.others;

/**
 * Created by vedant on 1/19/2018.
 */

public class CallbackDetails {
    public String phone,imei,date,packageid;

    public CallbackDetails(String phone, String imei, String date, String packageid) {
        this.phone = phone;
        this.imei = imei;
        this.date = date;
        this.packageid = packageid;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }
}
