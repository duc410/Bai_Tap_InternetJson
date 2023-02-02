package com.example.asynctaskexamples;

import java.io.Serializable;

public class UserInfo  implements Serializable {

    int id;
    String thumbNail;
    String photo;
    String email;
    String phone;
    String name;
    String userName;
    String street;
    String city;
    double lat;
    double lng;

    public UserInfo(int id, String thumbNail, String photo, String email, String phone, String name, String userName, String street, String city, double lat, double lng) {
        this.id = id;
        this.thumbNail = thumbNail;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.userName = userName;
        this.street = street;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
