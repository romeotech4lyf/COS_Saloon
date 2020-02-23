package com.tech4lyf.cossaloon.Models;

public class EmployeeIncome {
    private String employeeId;
    private String date;
    private Integer income;

    public EmployeeIncome(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public EmployeeIncome (String employeeId, String date, Integer income) {
        this.employeeId = employeeId;
        this.date = date;
        this.income = income;
    }
}
