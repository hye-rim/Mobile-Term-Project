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

    public Product(String brand, String name, int price, int calorie) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
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

    @Override
    public String toString() {
        return "\""+ brand +
                ", " + name +
                ", " + price +
                ", " + calorie +
                "\"";
    }
}
