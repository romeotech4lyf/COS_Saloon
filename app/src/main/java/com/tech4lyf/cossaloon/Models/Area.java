package com.tech4lyf.cossaloon.Models;

public class Area {

    private String id;
    private String name;
    private String addedDate;

    public Area(String id, String name, String addedDate) {
        this.id = id;
        this.name = name;
        this.addedDate = addedDate;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public Area() {

    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
