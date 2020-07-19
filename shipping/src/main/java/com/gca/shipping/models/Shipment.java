package com.gca.shipping.models;

import java.util.UUID;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long cartId;
    private UUID trackingId;


    public Shipment() {
        trackingId = UUID.randomUUID();
    }

    public Shipment(Long cartId) {
        this.cartId = cartId;
    }

    public Long getId() {
        return this.id;
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


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Shipment)) {
            return false;
        }
        Shipment shipment = (Shipment) o;
        return Objects.equals(id, shipment.id) && Objects.equals(cartId, shipment.cartId) && Objects.equals(trackingId, shipment.trackingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, trackingId);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", cartId='" + getCartId() + "'" +
            ", trackingId='" + getTrackingId() + "'" +
            "}";
    }

}