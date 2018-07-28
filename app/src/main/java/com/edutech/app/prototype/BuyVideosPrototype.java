package com.edutech.app.prototype;

/**
 * Created by vedant on 2/9/17.
 */

public class BuyVideosPrototype {
    public String packageName;
    public String cbse;
    public String classX;
    public String subject;
    public String numVideos;
    public String numChapters;
    public String totalHours;
    public String cbseGudilines;
    public String packagePath;
    public boolean isActivated;

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getCbse() {
        return cbse;
    }

    public void setCbse(String cbse) {
        this.cbse = cbse;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNumVideos() {
        return numVideos;
    }

    public void setNumVideos(String numVideos) {
        this.numVideos = numVideos;
    }

    public String getNumChapters() {
        return numChapters;
    }

    public void setNumChapters(String numChapters) {
        this.numChapters = numChapters;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public String getCbseGudilines() {
        return cbseGudilines;
    }

    public void setCbseGudilines(String cbseGudilines) {
        this.cbseGudilines = cbseGudilines;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public BuyVideosPrototype(String cbse,String classX,String subject,
                              String numVideos,String numChapters,String totalHours,
                              String packageName,String cbseGudilines,String packagePath,boolean isActivated){
        this.packageName = packageName;
        this.packagePath = packagePath;
        this.totalHours = totalHours;
        this.numChapters = numChapters;
        this.cbse = cbse;
        this.subject = subject;
        this.classX = classX;
        this.numVideos = numVideos;
        this.cbseGudilines = cbseGudilines;
        this.isActivated = isActivated;

    }
}
