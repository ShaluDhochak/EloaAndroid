package com.example.user.eloaandroid.Beans;

/*
  Created by Shalu Dhochak on 3/30/2018.
*/

public class RegisterBean {

    boolean status;
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public Data data;
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        String name,email,address,law_firm_name,phone,area_of_low_id,password,social_login_type,social_api,user_id;

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
        public String getUser_id() {
            return user_id;
        }
        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }

}

/*
{
    "status": true,
    "data": {
        "": "shalu",
        "": "shalu@gmail.com",
        "": "Pune",
        "": "eloa Developer",
        "": "7788778877",
        "": "",
        "": "123",
        "": null,
        "": null,
        "": 64
    }
}
 */