package com.mobile.hyerim.mobiletermproject.Data;

import java.io.Serializable;

/**
 * Created by HYERIM on 2016-12-21.
 */

public class Product implements Serializable {
    private String brand;
    private String name;
    private int price;
    private int calorie;
    private int imageNum;

    public Product(String brand, String name, int price, int calorie) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
        this.imageNum = 0;
    }

    public Product(String brand, String name, int price, int calorie, int imageNum) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
        this.imageNum = imageNum;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCalorie() {
        return calorie;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    @Override
    public String toString() {
        return "\""+ brand +
                ", " + name +
                ", " + price +
                ", " + calorie +
                "\"";
    }
}
