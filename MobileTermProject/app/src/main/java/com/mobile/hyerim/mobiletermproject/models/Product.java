package com.mobile.hyerim.mobiletermproject.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HYERIM on 2016-12-21.
 */

public class Product implements Serializable {
    private final String TAG = Product.class.getSimpleName();

    //상품 Unique ID
    public String productUid;

    //상품 브랜드 Unique ID
    public String brandUid;

    //상품 명
    public String name;

    //가격
    public int price;

    //칼로리
    public int calorie;

    //상품 이미지 id
    public int imageId;

    public Product() {
    }

    public Product(String productUid, String brandUid, String name, int price, int calorie) {
        this.productUid = productUid;
        this.brandUid = brandUid;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
        this.imageId = 0;
    }

    public Product(String productUid, String brandUid, String name, int price, int calorie, int imageId) {
        this.productUid = productUid;
        this.brandUid = brandUid;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
        this.imageId = imageId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("product_uid", productUid);
        result.put("brand_uid", brandUid);
        result.put("name", name);
        result.put("price", price);
        result.put("calorie", calorie);
        result.put("image_id", imageId);

        return result;
    }

    @Exclude
    public static Product parseSnapshot(DataSnapshot snapshot) {
        Product product = new Product();
        product.productUid = (String) snapshot.child("product_uid").getValue();
        product.brandUid = (String) snapshot.child("brand_uid").getValue();
        product.name = (String) snapshot.child("name").getValue();
        product.price = (int) snapshot.child("price").getValue();
        product.calorie = (int) snapshot.child("calorie").getValue();
        product.imageId = (int) snapshot.child("image_id").getValue();

        return product;
    }
}
