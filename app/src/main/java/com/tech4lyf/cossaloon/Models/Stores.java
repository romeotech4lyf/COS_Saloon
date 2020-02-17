package com.tech4lyf.cossaloon.Models;

public class Stores {
    private String Key;
    private String area;

    public Stores(String key, String area) {
        this.Key = key;
        this.area = area;
    }

    public String getKey() {
        return Key;
    }

    public String getArea() {
        return area;
    }
}
