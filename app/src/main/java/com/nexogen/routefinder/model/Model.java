package com.nexogen.routefinder.model;

public class Model {


    private String name;
    private boolean selected;
    private  int id;

    public Model(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", selected=" + selected +
                ", id=" + id +
                '}';
    }
}
