package com.example.minhq.mq_shop.Model;

/**
 * Created by minhq on 10/15/2017.
 */

public class Attribute {
    public int attributeID;
    public String nameattribute;
    public int productID;
    public String value;

    public Attribute(int attributeID, String nameattribute, int productID, String value) {
        this.attributeID = attributeID;
        this.nameattribute = nameattribute;
        this.productID = productID;
        this.value = value;
    }

    public int getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(int attributeID) {
        this.attributeID = attributeID;
    }

    public String getNameattribute() {
        return nameattribute;
    }

    public void setNameattribute(String nameattribute) {
        this.nameattribute = nameattribute;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
