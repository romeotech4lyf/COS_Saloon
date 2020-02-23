package com.tech4lyf.cossaloon.Models;

public class Stores {
    private String areaName;
    private Integer incomeToday;
    private Integer incomeMonthly;
    private String id;
    private String name;
    private String areaId;

    public Stores() {

    }

    public Stores(String id, String name, String areaId, String areaName, Integer incomeToday, Integer incomeMonthly) {

        this.name = name;
        this.areaName = areaName;
        this.incomeToday = incomeToday;
        this.areaId = areaId;
        this.incomeMonthly = incomeMonthly;
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getIncomeToday() {
        return incomeToday;
    }

    public void setIncomeToday(Integer incomeToday) {
        this.incomeToday = incomeToday;
    }

    public Integer getIncomeMonthly() {
        return incomeMonthly;
    }

    public void setIncomeMonthly(Integer incomeMonthly) {
        this.incomeMonthly = incomeMonthly;
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
