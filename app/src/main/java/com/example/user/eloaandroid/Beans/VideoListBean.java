package com.example.user.eloaandroid.Beans;

/*
  Created by Shalu Dhochak on 3/5/2018.
 */

public class VideoListBean {
    public String getVideoHeading() {
        return videoHeading;
    }

    public void setVideoHeading(String videoHeading) {
        this.videoHeading = videoHeading;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public VideoListBean(String videoHeading, String time, String day, String title, String description, String keywords) {
        this.videoHeading = videoHeading;
        this.time = time;
        this.day = day;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
    }

    public VideoListBean(){

    }

    String videoHeading, time, day,title, description, keywords;
}
