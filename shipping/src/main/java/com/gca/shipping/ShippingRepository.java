package com.gca.shipping;

import com.gca.shipping.models.Shipment;

import org.springframework.data.repository.CrudRepository;

public interface ShippingRepository extends CrudRepository<Shipment, Long>{
    
}