package com.nexogen.routefinder.model;



public class BookMarkModel {

    private String address;

    public BookMarkModel(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BookMarkModel{" +
                "address='" + address + '\'' +
                '}';
    }
}
