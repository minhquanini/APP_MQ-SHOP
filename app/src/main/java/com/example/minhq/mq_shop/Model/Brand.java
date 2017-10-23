package com.example.minhq.mq_shop.Model;

/**
 * Created by minhq on 10/12/2017.
 */

public class Brand {
    public int brandID;
    public String namebrand;
    public String image;

    public Brand(int brandID, String namebrand, String image) {
        this.brandID = brandID;
        this.namebrand = namebrand;
        this.image = image;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getNamebrand() {
        return namebrand;
    }

    public void setNamebrand(String namebrand) {
        this.namebrand = namebrand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
