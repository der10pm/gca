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
@RequestMapping("/api")
@RestController
public class CatalogController {

    private ArrayList<ShopItem> catalog = new ArrayList<>();

    @Value("${DOCKER_USER:catalog}")
    private String DOCKER_USER;

    @Value("${DOCKER_PW:catalog}")
    private String DOCKER_PW;

    @Value("${DOCKER_TIMEOUT:0}")
    private Long DOCKER_TIMEOUT;
    
    public CatalogController() {
        catalog.add(new ShopItem(1L, "Äpfel", 3000, "https://cdn.pixabay.com/photo/2017/09/26/13/42/apple-2788662_960_720.jpg"));
        catalog.add(new ShopItem(2L, "Birnen", 1500, "https://cdn.pixabay.com/photo/2017/04/13/23/02/pear-2228918_960_720.jpg"));
        catalog.add(new ShopItem(3L, "Bananen", 5000, "https://cdn.pixabay.com/photo/2016/09/03/20/48/bananas-1642706_960_720.jpg"));
        catalog.add(new ShopItem(4L, "Kiwis", 5000, "https://cdn.pixabay.com/photo/2016/05/19/12/36/kiwi-1402838_960_720.jpg"));
        catalog.add(new ShopItem(5L, "Kirschen", 3000, "https://cdn.pixabay.com/photo/2018/07/07/15/53/cherries-3522365_960_720.jpg"));
        catalog.add(new ShopItem(6L, "Wassermelonen", 2000, "https://cdn.pixabay.com/photo/2015/06/19/16/48/watermelon-815072_960_720.jpg"));
        catalog.add(new ShopItem(7L, "Zitronen", 5000, "https://cdn.pixabay.com/photo/2017/02/05/12/31/lemons-2039830_960_720.jpg"));
        catalog.add(new ShopItem(8L, "Orangen", 3000, "https://cdn.pixabay.com/photo/2017/01/20/15/12/orange-1995079_960_720.jpg"));
        catalog.add(new ShopItem(9L, "Weintrauben", 1000, "https://cdn.pixabay.com/photo/2018/09/22/23/43/grapes-3696472_960_720.jpg"));
        catalog.add(new ShopItem(10L, "Drachenfrucht", 7090, "https://cdn.pixabay.com/photo/2017/07/06/10/01/fruit-2477515_960_720.jpg"));
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all itemms from catalog", response = ShopItem[].class)
    public Collection<ShopItem> GetCatalog() {
        try {
            Thread.sleep(DOCKER_TIMEOUT);
        } catch (Exception e) {}
        return catalog;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get single item from catalog", response = ShopItem.class)
    public ShopItem GetItem(@PathVariable final Long id) {
        for (ShopItem item : catalog) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    @GetMapping("/health")
    @ApiOperation(value = "Check for API running", response = Map.class)
    public Map<String, String> CheckHealth() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "api is running");
        return map;

    }
}