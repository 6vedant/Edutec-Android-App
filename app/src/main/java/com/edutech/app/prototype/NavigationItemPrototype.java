package com.edutech.app.prototype;

/**
 * Created by vedant on 31/8/17.
 */

public class NavigationItemPrototype {
    public String title;
    public int image_id;

    public NavigationItemPrototype(String title, int image_id) {
        this.title = title;
        this.image_id = image_id;
    }

    public int getImage_id() {

        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public NavigationItemPrototype(String title){
        this.title = title;
    }
}

