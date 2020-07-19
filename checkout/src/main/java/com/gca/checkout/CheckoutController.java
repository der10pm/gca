package com.gca.checkout;

import java.net.SocketTimeoutException;
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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
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
        LOG.debug("Request started");
        ShopCart cart;
        Item[] items;

        try {
            cart = getCart(cartId);
        } catch (Exception e) {
            LOG.error("Error getting cart", cartId);
            LOG.error(e.getMessage());
            return null;
        }
        try {
            items = getCatalog();
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
                cartPrice += item.getPrice();
            }
        }

        Order order = new Order();
        order.setUserInfo(body);
        order.setCartPrice(cartPrice);
        order.setItems(cartItems);

        try {
            order.setShippingCost(getCost(cartPrice));
        } catch (Exception e) {
            LOG.error("Error getting shippingCost", cartPrice);
            LOG.error(e.getMessage());
            return null;
        }

        try {
            order.setShipment(getShipment(cartId));
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

    @Retryable(
        value = {SocketTimeoutException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    private ShopCart getCart(Long id) {
        ShopCart cart;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_CART_USER + ":" + DOCKER_CART_PW).getBytes())));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        cart = restTemplate.exchange("http://" + cart_Url + ":8081/api/cart/{id}", HttpMethod.GET, entity, ShopCart.class, id).getBody();
        if (cart == null) cart = new ShopCart();
        return cart;
    }

    @Recover
    private ShopCart getRecoveryCart(SocketTimeoutException e) {
        return new ShopCart();
    }
    
    @Retryable(
        value = {SocketTimeoutException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    private Item[] getCatalog() {
        Item[] items;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_CATALOG_USER + ":" + DOCKER_CATALOG_PW).getBytes())));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<Item[]> response = restTemplate.exchange("http://" + catalog_Url + ":8080/api/catalog", HttpMethod.GET, entity, Item[].class);
        items = response.getBody();
        if (items == null || items.length == 0) {
            items = new Item[0];
        }
        return items;
    }

    @Recover
    private Item[] getRecoveryCatalog(SocketTimeoutException e) {
        return new Item[0];
    }

    @Retryable(
        value = {SocketTimeoutException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    private Long getCost(Long cartPrice) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_SHIPPING_USER + ":" + DOCKER_SHIPPING_PW).getBytes())));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("http://" + shipping_Url + ":8082/api/shipping/cost/?cost={cost}", HttpMethod.GET, entity, Long.class, cartPrice).getBody();
    }

    @Recover
    private Long getRecoveryCost(SocketTimeoutException e) {
        return 0L;
    }

    @Retryable(
        value = {SocketTimeoutException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    private Shipment getShipment(Long cartId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(new String(Base64.getEncoder().encode((DOCKER_SHIPPING_USER + ":" + DOCKER_SHIPPING_PW).getBytes())));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("http://"+ shipping_Url +":8082/api/shipping/?cartId=" + cartId, HttpMethod.POST, entity, Shipment.class).getBody();
    }

    @Recover
    private Shipment getRecoveryShipment(SocketTimeoutException e) {
        return new Shipment();
    }
}