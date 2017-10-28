package com.example.minhq.mq_shop.Model;

/**
 * Created by minhq on 10/16/2017.
 */

public class OrderDetail {
    public int idpd;
    public String namepd;
    public Double pricepd;
    public String imagepd;
    public int quantitypd;
    public int quantitymax;

    public OrderDetail(int idpd, String namepd, Double pricepd, String imagepd, int quantitypd, int quantitymax) {
        this.idpd = idpd;
        this.namepd = namepd;
        this.pricepd = pricepd;
        this.imagepd = imagepd;
        this.quantitypd = quantitypd;
        this.quantitymax=quantitymax;
    }

    public int getIdpd() {
        return idpd;
    }

    public void setIdpd(int idpd) {
        this.idpd = idpd;
    }

    public String getNamepd() {
        return namepd;
    }

    public void setNamepd(String namepd) {
        this.namepd = namepd;
    }

    public Double getPricepd() {
        return pricepd;
    }

    public void setPricepd(Double pricepd) {
        this.pricepd = pricepd;
    }

    public String getImagepd() {
        return imagepd;
    }

    public void setImagepd(String imagepd) {
        this.imagepd = imagepd;
    }

    public int getQuantitypd() {
        return quantitypd;
    }

    public void setQuantitypd(int quantitypd) {
        this.quantitypd = quantitypd;
    }

    public int getQuantitymax() {
        return quantitymax;
    }

    public void setQuantitymax(int quantitymax) {
        this.quantitymax = quantitymax;
    }
}
