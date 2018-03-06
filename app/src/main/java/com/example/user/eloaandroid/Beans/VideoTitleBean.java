package com.example.user.eloaandroid.Beans;

/**
 * Created by User on 3/6/2018.
 */

public class VideoTitleBean {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VideoTitleBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public VideoTitleBean() {
    }

    String id, name;
}
