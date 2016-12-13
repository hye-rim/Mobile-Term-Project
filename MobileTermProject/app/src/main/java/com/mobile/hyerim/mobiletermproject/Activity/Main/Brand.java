package com.mobile.hyerim.mobiletermproject.Activity.Main;

/**
 * Created by HYERIM on 2016-12-13.
 */

public class Brand {
    String brandNameEng;
    String brandNameKo;
    int imageResourceId;

    public Brand(String brandNameEng, String brandNameKo, int imageResourceId) {
        this.brandNameEng = brandNameEng;
        this.brandNameKo = brandNameKo;
        this.imageResourceId = imageResourceId;
    }

    public String getBrandNameEng() {
        return brandNameEng;
    }

    public void setBrandNameEng(String brandNameEng) {
        this.brandNameEng = brandNameEng;
    }

    public String getBrandNameKo() {
        return brandNameKo;
    }

    public void setBrandNameKo(String brandNameKo) {
        this.brandNameKo = brandNameKo;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
