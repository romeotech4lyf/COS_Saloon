package com.tech4lyf.cossaloon.Models;

public class User {
    String key;
    String username;
    String  password;
    String isAdmin;
    String name;


    public User(String key, String username, String password, String name, String isAdmin) {
        this.key = key;
        this.username= username;
        this.password= password;
        this.isAdmin=isAdmin;
        this.name=name;
    }


    public User() {
    }



    public String getKey() {
        return key;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIsAdmin() { return isAdmin; }

    public String getName() { return name; }
}