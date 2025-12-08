package com.ms.orderservice.controller;


import com.ms.orderservice.dto.CartItemRequest;
import com.ms.orderservice.dto.CartItemResponse;
import com.ms.orderservice.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItemInCart(@RequestHeader("X-USER-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse addedResponse = cartItemService.addItemToCart(userId, cartItemRequest);
        return new ResponseEntity<>(addedResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteItem/{product_id}")
    public ResponseEntity<?> deleteItemFromCart(@RequestHeader("X-USER-ID") String userId,
                                               @PathVariable("product_id") String productId){
       return cartItemService.deleteItemFromCart(userId,productId) ?
               new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getItemsFromCartByUser(@RequestHeader("X-USER-ID") String userId){
        List<CartItemResponse> getCartItemsByUser = cartItemService.getCartItems(userId);
        if(getCartItemsByUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getCartItemsByUser,HttpStatus.OK);
    }
}
