package com.ecomapp.service;

import com.ecomapp.dto.CartItemRequest;
import com.ecomapp.dto.CartItemResponse;
import com.ecomapp.entity.CartItem;

import java.util.List;

public interface CartItemService {

    CartItemResponse addItemToCart(String userId,
                                   CartItemRequest cartItemRequest);

    boolean deleteItemFromCart(String userId, Long productId);

    List<CartItemResponse> getCartItems(String userId);

    List<CartItem> getAllCartItemsByUser(String userId);

    void clearCartByUser(String userId);
}
