package com.mobile.hyerim.mobiletermproject.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HYERIM on 2016-12-13.
 */

public class Brand {
    private final String TAG = Brand.class.getSimpleName();

    //브랜드 Unique ID
    public String brandUid;

    //브랜드 영어 이름
    public String nameEng;

    //브랜드 한글 이름
    public String nameKo;

    //브랜드 로고 이미지
    public int logoResourceId;

    public Brand() {
    }

    public Brand(String brandUid, String nameEng, String nameKo, int imageResourceId) {
        this.brandUid = brandUid;
        this.nameEng = nameEng;
        this.nameKo = nameKo;
        this.logoResourceId = imageResourceId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("brand_uid", brandUid);
        result.put("name_eng", nameEng);
        result.put("name_ko", nameKo);
        result.put("logo_resource_id", logoResourceId);

        return result;
    }

    @Exclude
    public static Brand parseSnapshot(DataSnapshot snapshot) {
        Brand brand = new Brand();
        brand.brandUid = (String) snapshot.child("brand_uid").getValue();
        brand.nameEng = (String) snapshot.child("name_eng").getValue();
        brand.nameKo = (String) snapshot.child("name_ko").getValue();
        brand.logoResourceId = (int) snapshot.child("logo_resource_id").getValue();

        return brand;
    }
}
