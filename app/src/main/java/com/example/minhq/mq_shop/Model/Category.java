package com.example.minhq.mq_shop.Model;

/**
 * Created by minhq on 10/9/2017.
 */

public class Category {
    public int categoryID;
    public String namecategory;
    //public int image;

    public Category(int categoryID, String namecategory) {
        this.categoryID = categoryID;
        this.namecategory = namecategory;
        //this.image=image;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getNamecategory() {
        return namecategory;
    }

    public void setNamecategory(String namecategory) {
        this.namecategory = namecategory;
    }



}
