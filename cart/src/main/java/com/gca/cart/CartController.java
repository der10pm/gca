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
@RequestMapping("/")
@RestController
public class CartController {

    @Autowired
    private CartRepository repository;

    /**
     * endpoint create new cart
     *
     * @return
     */
    @PostMapping("/")
    public @ResponseBody ShopCart PostCart(@RequestBody final Collection<Long> body) {

        final ShopCart temp = new ShopCart();
        temp.setItems(body);
        repository.save(temp);
        return temp;
    }

    /**
     * endpoint get all carts ()
     * 
     * @return
     */
    @GetMapping("/")
    public Iterable<ShopCart> GetCarts() {
        return repository.findAll();
    }

    /**
     * endpoint get cart by id
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<ShopCart> GetCart(@PathVariable final Long id) {
        return repository.findById(id);
    }

    /**
     * endpoint delete cart by id
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteCart(@PathVariable final Long id) {
        repository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * endpoint delete all carts
     * 
     * @return
     */
    @DeleteMapping("/")
    public ResponseEntity<String> DeleteAll() {

        repository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * endpoint add item id to cart
     * 
     * @param newItem
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ShopCart PutCart(@RequestParam("newItem") final Long newItem, @PathVariable final Long id) {
        final ShopCart temp = repository.findById(id).get();
        temp.getItems().add(newItem);
        repository.save(temp);
        return temp;
        // @RequestBody String pBody
    }

    @GetMapping("/health")
    @ApiOperation(value = "Check for API running", notes = "api check call", response = Map.class)
    public Map<String, String> CheckHealth() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("message", "api is running");
        return map;
    }
}