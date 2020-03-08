package com.tech4lyf.cossaloon.Models;

public class Store {
    private String areaName;
    private String id;
    private String name;
    private String areaId;
    private String addedDate;

    public Store() {

    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public Store(String id, String name, String areaId, String areaName , String addedDate) {

        this.name = name;
        this.areaName = areaName;
        this.areaId = areaId;
        this.addedDate = addedDate;
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
