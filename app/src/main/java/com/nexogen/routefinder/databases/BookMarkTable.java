package com.nexogen.routefinder.databases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nexogen on 19/12/17.
 */

@Entity
public class BookMarkTable {
    @PrimaryKey
    private int id;
    private String address;

    public BookMarkTable(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
