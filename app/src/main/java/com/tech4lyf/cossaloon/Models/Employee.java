package com.tech4lyf.cossaloon.Models;

public class Employee {


    private String joiningDate;
    private String name;
    private String id;
    private String storeId;
    private String storeName;
    private String areaId;
    private String phoneNumber;
    private String areaName;

    public Employee(String id, String name, String storeId, String storeName, String areaId, String areaName, String phoneNumber, String joiningDate) {

        this.id = id;
        this.storeName = storeName;
        this.joiningDate = joiningDate;
        this.name = name;
        this.areaName = areaName;
        this.phoneNumber = phoneNumber;
        this.areaId = areaId;
        this.storeId = storeId;
    }

    public Employee() {
        //Required Empty Constructor
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
