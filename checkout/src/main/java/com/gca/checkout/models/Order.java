package com.gca.checkout.models;

import java.util.Collection;

public class Order {
    private UserInfo userInfo;
    private Long cartPrice;
    private Long shippingCost;
    private Collection<Item> items;
    private Shipment shipment;

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getCartPrice() {
        return this.cartPrice;
    }

    public void setCartPrice(Long cartPrice) {
        this.cartPrice = cartPrice;
    }

    public Long getShippingCost() {
        return this.shippingCost;
    }

    public void setShippingCost(Long shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Collection<Item> getItems() {
        return this.items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Shipment getShipment() {
        return this.shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }


}