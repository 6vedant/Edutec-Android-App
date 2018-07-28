package com.edutech.app.prototype;

/**
 * Created by vedant on 5/9/17.
 */

public class Unable2FindPackagesPrototype {
    public String title;
    public String folderPath;
    public String foldername;

    public String getFoldername() {
        return foldername;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public Unable2FindPackagesPrototype(String title,String folderPath){
        this.title = title;
     //   this.foldername = foldername;
        this.folderPath = folderPath;
    }
}
