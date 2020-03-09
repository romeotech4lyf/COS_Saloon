package com.tech4lyf.cossaloon.Models;

public class Administrator {
    private String phoneNumber;
    private String password;
    private String dPUriString;

    public Administrator(String phoneNumber, String password, String dPUriString) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.dPUriString = dPUriString;
    }

    public Administrator() {

    }

    public String getdPUriString() {
        return dPUriString;
    }

    public void setdPUriString(String dPUriString) {
        this.dPUriString = dPUriString;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
