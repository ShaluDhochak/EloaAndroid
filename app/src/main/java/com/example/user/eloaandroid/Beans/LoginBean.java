package com.example.user.eloaandroid.Beans;

/*
  Created by Shalu Dhochak on 3/28/2018.
*/

import java.util.ArrayList;

public class LoginBean {

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    boolean status;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> data;

    public static class Data{
        String id, name, email, address, law_firm_name, phone, area_of_low_id, password, social_login_type, social_api, status, created_at;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLaw_firm_name() {
            return law_firm_name;
        }

        public void setLaw_firm_name(String law_firm_name) {
            this.law_firm_name = law_firm_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getArea_of_low_id() {
            return area_of_low_id;
        }

        public void setArea_of_low_id(String area_of_low_id) {
            this.area_of_low_id = area_of_low_id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSocial_login_type() {
            return social_login_type;
        }

        public void setSocial_login_type(String social_login_type) {
            this.social_login_type = social_login_type;
        }

        public String getSocial_api() {
            return social_api;
        }

        public void setSocial_api(String social_api) {
            this.social_api = social_api;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }


    }

}


/*
"status": true,
    "data": [
        {
            "": "37",
            "": "mehul",
            "": "mehul123@gmail.com",
            "": "surat",
            "": "test",
            "": "9879879877",
            "": "trettt",
            "": "202cb962ac59075b964b07152d234b70",
            "": "fb",
            "": "11",
            "": "0",
            "": "2018-03-19 05:25:42"
        }
    ]
 */