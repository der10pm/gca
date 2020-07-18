package com.gca.shipping;

import java.util.*;

import com.gca.shipping.models.Shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
@RestController
public class ShippingController {
    
    @Autowired
    private ShippingRepository repository;

    @PostMapping("/")
    public Shipment PostShipping(@RequestParam Long cartId) {
        Shipment s = new Shipment(cartId);        
        return repository.save(s);
    }

    @GetMapping("/cost")
    public Long CalculateShippingCost(@RequestParam Long cost) {
        Long ret = 0L;
        if (cost > 0 && cost <= 10000) {
            ret = 1000L;
        }
        return ret;
    }

    @GetMapping("/health")
    @ApiOperation(value = "Check for API running", notes = "api check call", response = Map.class)
    public Map<String, String> CheckHealth() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("message", "api is running");
        return map;
    }
}