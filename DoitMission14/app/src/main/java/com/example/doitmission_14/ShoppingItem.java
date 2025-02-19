package com.example.doitmission_14;

public class ShoppingItem {
    int image; //resId
    String name;
    String price;
    String descrpiton;

    public ShoppingItem(String name, String price, String descrpiton, int resId) {
        this.image = resId;
        this.descrpiton = descrpiton;
        this.name = name;
        this.price = price+"원";
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescrpiton() {
        return descrpiton;
    }

    public void setDescrpiton(String descrpiton) {
        this.descrpiton = descrpiton;
    }
}
