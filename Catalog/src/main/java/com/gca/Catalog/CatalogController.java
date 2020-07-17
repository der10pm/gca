package com.gca.Catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gca.Catalog.models.ShopItem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/health")
    @ApiOperation(value = "Check for API running", notes = "api check call", response = Map.class)
    public Map<String, String> CheckHealth() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "api is running");
        map.put("USER", this.DOCKER_USER);
        map.put("PW", this.DOCKER_PW);
        return map;

    }
}