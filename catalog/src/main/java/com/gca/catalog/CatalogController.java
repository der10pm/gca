package com.gca.catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gca.catalog.models.ShopItem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
@RestController
public class CatalogController {

    private ArrayList<ShopItem> cList = new ArrayList<>();

    @Value("${DOCKER_USER:catalog}")
    private String DOCKER_USER;

    @Value("${DOCKER_PW:catalog}")
    private String DOCKER_PW;
    
    public CatalogController() {

    }

    @GetMapping("/")
    public Collection<ShopItem> GetCatalog() {
        return cList;
    }

    @GetMapping("/{id}")
    public ShopItem GetItem(@PathVariable final Long id) {
        for (ShopItem item : cList) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    @GetMapping("/health")
    @ApiOperation(value = "Check for API running", notes = "api check call", response = Map.class)
    public Map<String, String> CheckHealth() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "api is running");
        return map;

    }
}