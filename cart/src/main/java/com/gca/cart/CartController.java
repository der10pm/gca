package com.gca.cart;

import java.util.*;

import com.gca.cart.models.ShopCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
@RestController
public class CartController {

    @Autowired
    private CartRepository repository;

    @PostMapping("/")
    @ApiOperation(value = "Post new cart", response = ShopCart.class)
    public @ResponseBody ShopCart PostCart(@RequestBody final Collection<Long> body) {

        final ShopCart temp = new ShopCart();
        temp.setItems(body);
        repository.save(temp);
        return temp;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all saved carts", response = ShopCart[].class)
    public Iterable<ShopCart> GetCarts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get cart with specified id", response = ShopCart.class)
    public Optional<ShopCart> GetCart(@PathVariable final Long id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Cart")
    public ResponseEntity<String> DeleteCart(@PathVariable final Long id) {
        repository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/")
    @ApiOperation(value = "Delete all carts")
    public ResponseEntity<String> DeleteAll() {

        repository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Add Item to cart", response = ShopCart.class)
    public ShopCart PutCart(@RequestParam("newItem") final Long newItem, @PathVariable final Long id) {
        final ShopCart temp = repository.findById(id).get();
        temp.getItems().add(newItem);
        
        return repository.save(temp);
    }

    @GetMapping("/health")
    @ApiOperation(value = "Check for API running", notes = "api check call", response = Map.class)
    public Map<String, String> CheckHealth() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("message", "api is running");
        return map;
    }
}