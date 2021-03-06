package com.gca.catalog.models;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

public class ShopItem {

    @ApiModelProperty(notes = "Item ID")
    private Long id;
    @ApiModelProperty(notes = "Item name")
    private String name;
    @ApiModelProperty(notes = "Item price in cents")
    private int price;
    @ApiModelProperty(notes = "Item image")
    private String imageURL;

    public ShopItem() {
    }

    public ShopItem(Long id, String name, int price, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ShopItem)) {
            return false;
        }
        ShopItem shopItem = (ShopItem) o;
        return Objects.equals(id, shopItem.id) && Objects.equals(name, shopItem.name) && price == shopItem.price && Objects.equals(imageURL, shopItem.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageURL);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", imageURL='" + getImageURL() + "'" +
            "}";
    }

    
}