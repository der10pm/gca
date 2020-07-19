package com.gca.checkout.models;

import java.util.UUID;

public class Shipment {
    private Long id;
    private Long cartId;
    private UUID trackingId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return this.cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public UUID getTrackingId() {
        return this.trackingId;
    }

    public void setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
    }

}