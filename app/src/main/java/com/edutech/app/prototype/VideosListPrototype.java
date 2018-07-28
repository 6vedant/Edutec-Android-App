package com.edutech.app.prototype;

/**
 * Created by vedant on 1/9/17.
 */

public class VideosListPrototype {
    public String title,thumbnail_url,video_url;
    boolean activated;

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public VideosListPrototype(String title, String thumbnail_url, String video_url,boolean activated){
        this.title = title;
        this.thumbnail_url = thumbnail_url;
        this.video_url = video_url;
        this.activated = activated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
