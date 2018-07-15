package com.nexogen.routefinder.model;

/**
 * Created by nexogen on 20/12/17.
 */

public class NavigatorModel {

    private int id;
    private String source;
    private String destination;
    private String dateTime;
    private boolean selected;


    public NavigatorModel(int id, String source, String destination, String dateTime) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "NavigatorModel{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", selected=" + selected +
                '}';
    }
}
