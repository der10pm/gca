package com.gca.checkout;

import java.util.*;

import com.gca.checkout.models.Item;
import com.gca.checkout.models.Order;
import com.gca.checkout.models.Shipment;
import com.gca.checkout.models.ShopCart;
import com.gca.checkout.models.UserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
@RestController
public class CheckoutController {

    @Value("${DOCKER_CATALOG_URL:localhost}")
    private String catalog_Url;

    @Value("${DOCKER_CART_URL:localhost}")
    private String cart_Url;

    @Value("${DOCKER_SHIPPING_URL:localhost}")
    private String shipping_Url;

    @Value("${DOCKER_SHIPPING_USER:shipping}")
    private String DOCKER_SHIPPING_USER;

    @Value("${DOCKER_SHIPPING_PW:shipping}")
    private String DOCKER_SHIPPING_PW;

    @Value("${DOCKER_CATALOG_USER:catalog}")
    private String DOCKER_CATALOG_USER;

    @Value("${DOCKER_CATALOG_PW:catalog}")
    private String DOCKER_CATALOG_PW;

    @Value("${DOCKER_CART_USER:cart}")
    private String DOCKER_CART_USER;

    @Value("${DOCKER_CART_PW:cart}")
    private String DOCKER_CART_PW;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(CheckoutController.class);

    @PostMapping("/{cartId}")
    public Order postCheckout(@RequestBody UserInfo body, @PathVariable Long cartId) {
        ShopCart cart;
        Item[] items;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_CART_USER + ":" + DOCKER_CART_PW).getBytes())));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            cart = restTemplate.exchange("http://" + cart_Url + "api/Carts/{cartId}", HttpMethod.GET, entity, ShopCart.class, cartId).getBody();
        } catch (Exception e) {
            LOG.error("Error getting cart", cartId);
            LOG.error(e.getMessage());
            return null;
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_CATALOG_USER + ":" + DOCKER_CATALOG_PW).getBytes())));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<Item[]> response = restTemplate.exchange("http://" + catalog_Url + "api/Catalog", HttpMethod.GET, entity, Item[].class);
            items = response.getBody();
        } catch (Exception e) {
            LOG.error("Error getting catalog");
            LOG.error(e.getMessage());
            return null;
        }

        ArrayList<Item> cartItems = new ArrayList<Item>();
        Long cartPrice = Long.valueOf(0);

        for (Item item : items) {
            if (cart.getItems().contains(item.getId())) {
                cartItems.add(item);
                /**
                 * accumulate price
                 */
                cartPrice += item.getPrice();
            }
        }

        Order order = new Order();
        order.setUserInfo(body);
        order.setCartPrice(cartPrice);
        order.setItems(cartItems);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_SHIPPING_USER + ":" + DOCKER_SHIPPING_PW).getBytes())));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            order.setShippingCost(restTemplate.exchange("http://" + shipping_Url + "api/Shipping/{cost}", HttpMethod.GET, entity, Long.class, cartPrice).getBody());
        } catch (Exception e) {
            LOG.error("Error getting shippingCost", cartPrice);
            LOG.error(e.getMessage());
            return null;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_SHIPPING_USER + ":" + DOCKER_SHIPPING_PW).getBytes())));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            order.setShipment(restTemplate.exchange("http://"+ shipping_Url +"/api/shipping/?cartId=" + cartId, HttpMethod.POST, entity, Shipment.class).getBody());
        } catch (Exception e) {
            LOG.error("Error post shipping");
            LOG.error(e.getMessage());
            return null;
        }

        return order;
    }

    @GetMapping("/health")
    @ApiOperation(value = "Check for API running", notes = "api check call", response = Map.class)
    public Map<String, String> CheckHealth() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("message", "api is running");
        return map;
    }
}