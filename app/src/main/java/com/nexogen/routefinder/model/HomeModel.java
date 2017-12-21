package com.nexogen.routefinder.model;


public class HomeModel {

    public int id;
    public String bnakName;
    public String image;

    public HomeModel(int id, String bnakName, String image) {
        this.id = id;
        this.bnakName = bnakName;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBnakName() {
        return bnakName;
    }

    public void setBnakName(String bnakName) {
        this.bnakName = bnakName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
