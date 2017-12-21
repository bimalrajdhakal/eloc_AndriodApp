package com.nexogen.routefinder.model;

/**
 * Created by nexogen on 8/12/17.
 */

class LocationHistoryModel {
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String date;
    private String time;

    public LocationHistoryModel(String address, String city, String postalCode, String country, String date, String time) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.date = date;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LocationHistoryModel{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}


