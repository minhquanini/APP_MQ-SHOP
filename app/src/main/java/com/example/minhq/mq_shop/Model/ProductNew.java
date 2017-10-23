package com.example.minhq.mq_shop.Model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by minhq on 10/12/2017.
 */

public class ProductNew implements Serializable{
    public int productID;
    public String nameproduct;
    public Double price;
    public Double promotionprice;
    public String imageproduct;
    public String descriptionproduct;
    public String contentproduct;

    public ProductNew(int productID, String nameproduct, Double price, Double promotionprice, String imageproduct, String descriptionproduct, String contentproduct) {
        this.productID = productID;
        this.nameproduct = nameproduct;
        this.price = price;
        this.promotionprice=promotionprice;
        this.imageproduct = imageproduct;
        this.descriptionproduct=descriptionproduct;
        this.contentproduct=contentproduct;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPromotionprice() {
        return promotionprice;
    }

    public void setPromotionprice(Double promotionprice) {
        this.promotionprice = promotionprice;
    }

    public String getImageproduct() {
        return imageproduct;
    }

    public void setImageproduct(String imageproduct) {
        this.imageproduct = imageproduct;
    }

    public String getDescriptionproduct() {
        return descriptionproduct;
    }

    public void setDescriptionproduct(String descriptionproduct) {
        this.descriptionproduct = descriptionproduct;
    }

    public String getContentproduct() {
        return contentproduct;
    }

    public void setContentproduct(String contentproduct) {
        this.contentproduct = contentproduct;
    }
}
