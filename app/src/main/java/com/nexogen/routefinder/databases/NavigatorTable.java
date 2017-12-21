package com.nexogen.routefinder.databases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nexogen on 19/12/17.
 */
@Entity
public class NavigatorTable {
    @PrimaryKey
    private int id;
    private String source;
    private String destination;
    private String dateTime;

    public NavigatorTable(int id, String source, String destination, String dateTime) {
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

    @Override
    public String toString() {
        return "NavigatorTable{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
