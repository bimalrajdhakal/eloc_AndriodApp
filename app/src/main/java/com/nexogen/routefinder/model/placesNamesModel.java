package com.nexogen.routefinder.model;

/**
 * Created by nexogen on 26/12/17.
 */

public class placesNamesModel {

    private  String placeName;

    private int id;

    public placesNamesModel(String placeName, int id) {
        this.placeName = placeName;
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "placesNamesModel{" +
                "placeName='" + placeName + '\'' +
                ", id=" + id +
                '}';
    }
}
