package com.gca.catalog.models;

import java.util.Objects;

public class ShopItem {

    private Long id;
    private String name;
    private int priceInCent;
    private String imageURL;

    public ShopItem() {
    }

    public ShopItem(Long id, String name, int priceInCent, String imageURL) {
        this.id = id;
        this.name = name;
        this.priceInCent = priceInCent;
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

    public int getPriceInCent() {
        return this.priceInCent;
    }

    public void setPriceInCent(int priceInCent) {
        this.priceInCent = priceInCent;
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
        return Objects.equals(id, shopItem.id) && Objects.equals(name, shopItem.name) && priceInCent == shopItem.priceInCent && Objects.equals(imageURL, shopItem.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, priceInCent, imageURL);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", priceInCent='" + getPriceInCent() + "'" +
            ", imageURL='" + getImageURL() + "'" +
            "}";
    }

    
}