package com.example.minhq.mq_shop.Model;

import java.io.Serializable;

/**
 * Created by minhq on 10/13/2017.
 */

public class Product implements Serializable{
    public int productID;
    public String nameproduct;
    public Double price;
    public Double promotionprice;
    public int quantity;
    public String imageproduct;
    public String descriptionproduct;
    public String contentproduct;

    public Product(int productID, String nameproduct, String imageproduct, Double price, Double promotionprice,int quantity, String descriptionproduct, String contentproduct) {
        this.productID = productID;
        this.nameproduct = nameproduct;
        this.imageproduct = imageproduct;
        this.price = price;
        this.promotionprice=promotionprice;
        this.quantity=quantity;
        this.descriptionproduct = descriptionproduct;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
