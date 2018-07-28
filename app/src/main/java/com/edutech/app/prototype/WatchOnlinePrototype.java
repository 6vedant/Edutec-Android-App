package com.edutech.app.prototype;

import com.edutech.app.activities.WatchOnline;

/**
 * Created by vedant on 1/10/2018.
 */

public class WatchOnlinePrototype {
    public String video_title,video_url,thumb_url;

    public WatchOnlinePrototype(){}

    public WatchOnlinePrototype(String video_title, String video_url, String thumb_url) {
        this.video_title = video_title;
        this.video_url = video_url;
        this.thumb_url = thumb_url;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }
}
