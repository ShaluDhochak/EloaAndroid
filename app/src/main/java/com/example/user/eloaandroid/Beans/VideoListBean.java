package com.example.user.eloaandroid.Beans;

/*
  Created by Shalu Dhochak on 3/5/2018.
 */

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

public class VideoListBean {

    public boolean status;
    public ArrayList<Data> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data{
        String id, user_id, video, keyword, title, description, location, date;
        Bitmap bitmap;

        public Data(String id, String user_id, String video, String keyword, String title, String description, String location, String date) {
            this.id = id;
            this.user_id = user_id;
            this.video = video;
            this.keyword = keyword;
            this.title = title;
            this.description = description;
            this.location = location;
            this.date = date;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }



}
