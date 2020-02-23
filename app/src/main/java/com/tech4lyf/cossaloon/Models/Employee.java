package com.tech4lyf.cossaloon.Models;

public class Employee {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    private String joiningDate;
    private String name;
    private String id;
    private String storeId;
    private String storeName;

    public Employee(String id,String name,String storeId,String storeName,String joiningDate) {

        this.id = id;
        this.storeName = storeName;
        this.joiningDate = joiningDate;
        this.name = name;
        this.storeId = storeId;
    }

    public Employee() {
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
