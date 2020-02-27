package com.tech4lyf.cossaloon.Models;

import java.util.ArrayList;

public class Bill {
    private String id;
    private String areaId;
    private String storeId;
    private String employeeId;
    private String time;
    private String date;
    private String areaName;
    private ArrayList<String> listItems;
    private ArrayList<Integer> listItemPrices;
    private Integer totalPrice;
    private String employeeName;
    private String storeName;

    public Bill(String id, String areaId, String areaName, String storeId, String storeName, String employeeId, String employeeName, String time, String date, ArrayList<String> listItems, ArrayList<Integer> listItemPrices, Integer totalPrice) {
        this.id = id;
        this.areaId = areaId;
        this.storeId = storeId;
        this.employeeId = employeeId;
        this.storeName = storeName;
        this.employeeName = employeeName;
        this.time = time;
        this.date = date;
        this.areaName = areaName;
        this.listItems = listItems;
        this.listItemPrices = listItemPrices;
        this.totalPrice = totalPrice;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Bill() {
        //Required Empty Constructor
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public ArrayList<String> getListItems() {
        return listItems;
    }

    public void setListItems(ArrayList<String> listItems) {
        this.listItems = listItems;
    }

    public ArrayList<Integer> getListItemPrices() {
        return listItemPrices;
    }

    public void setListItemPrices(ArrayList<Integer> listItemPrices) {
        this.listItemPrices = listItemPrices;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
