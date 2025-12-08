package com.ms.orderservice.service;



import com.ms.orderservice.dto.CartItemRequest;
import com.ms.orderservice.dto.CartItemResponse;
import com.ms.orderservice.entity.CartItem;

import java.util.List;

public interface CartItemService {

    CartItemResponse addItemToCart(String userId,
                                   CartItemRequest cartItemRequest);

    boolean deleteItemFromCart(String userId, String productId);

    List<CartItemResponse> getCartItems(String userId);

    List<CartItem> getAllCartItemsByUser(String userId);

    void clearCartByUser(String userId);
}
