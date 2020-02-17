package com.tech4lyf.cossaloon.Models;

public class Employee {

    String address;
    String joiningDate;
    String name;
    String phoneNumber;

    public String getName() {
        return name;
    }

   public Employee(){


    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public Employee(String name, String joiningDate, String address, String phoneNumber) {
        this.name = name;
        this.joiningDate = joiningDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
