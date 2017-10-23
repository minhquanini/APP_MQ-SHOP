package com.example.minhq.mq_shop.Model;

/**
 * Created by minhq on 10/10/2017.
 */

public class Menu {
    private String name;
    private int image;

    public Menu(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
