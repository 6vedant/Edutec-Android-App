package com.edutech.app.prototype;

import com.edutech.app.activities.OurShops;

/**
 * Created by vedant on 1/17/2018.
 */

public class OurShopsPrototype {
    public String shop_name,shop_address,shop_phone,shop_gpsx,shop_gpsy;
    public OurShopsPrototype(){}

    public String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public OurShopsPrototype(String shop_name, String shop_address, String shop_phone, String shop_gpsx, String shop_gpsy) {
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.shop_phone = shop_phone;
        this.shop_gpsx = shop_gpsx;
        this.shop_gpsy = shop_gpsy;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_gpsx() {
        return shop_gpsx;
    }

    public void setShop_gpsx(String shop_gpsx) {
        this.shop_gpsx = shop_gpsx;
    }

    public String getShop_gpsy() {
        return shop_gpsy;
    }

    public void setShop_gpsy(String shop_gpsy) {
        this.shop_gpsy = shop_gpsy;
    }
}
