package com.ms.orderservice.service;


import com.ms.orderservice.dto.CartItemRequest;
import com.ms.orderservice.dto.CartItemResponse;
import com.ms.orderservice.entity.CartItem;
import com.ms.orderservice.exception.ProductNotFoundException;
import com.ms.orderservice.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemServiceImp implements CartItemService {

    private final CartItemRepository cartItemRepository;


    public CartItemServiceImp(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItemResponse addItemToCart(String userId,
                                          CartItemRequest cartItemRequest) {

        BigDecimal totalPrice = BigDecimal.valueOf(0);

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(cartItemRequest.getProductId()));

        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+cartItemRequest.getQuantity());
//            totalPrice = product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity()));
            existingCartItem.setPrice(BigDecimal.valueOf(1000.0));
            cartItemRepository.save(existingCartItem);

            return new CartItemResponse(
                      existingCartItem.getId(),
                      existingCartItem.getProductId(),
                      existingCartItem.getQuantity(),
                      existingCartItem.getPrice());
        }

        CartItem newItem = new CartItem();
        newItem.setUserId(userId);
        newItem.setProductId(cartItemRequest.getProductId());
        newItem.setQuantity(cartItemRequest.getQuantity());

//        totalPrice = product.getPrice().multiply(new BigDecimal(cartItemRequest.getQuantity()));
        newItem.setPrice(BigDecimal.valueOf(1000.0));

        CartItem addedItem = cartItemRepository.save(newItem);

        return new CartItemResponse(
                 addedItem.getId(),
                 addedItem.getProductId(),
                 addedItem.getQuantity(),
                 addedItem.getPrice());
    }

    @Transactional
    @Override
    public boolean deleteItemFromCart(String userId, String productId) {

//        MyUsers findUser = myUserRepository.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new UserNotFoundException("User not found with the ID :" + userId));
//
//        Product product = productRepository
//                .findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with the ID : " + productId));

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);

        if(cartItem!=null){
            cartItemRepository.deleteByUserIdAndProductId(userId,productId);
            return true;
        }

        return false;
    }

    @Override
    public List<CartItemResponse> getCartItems(String userId) {

//        MyUsers findUser = myUserRepository.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new UserNotFoundException("User not found with the ID :" + userId));

        List<CartItem> cartItemsByUser = cartItemRepository.findAllByUserId(userId);

        List<CartItemResponse> cartItemResponseList = new ArrayList<>();

        for (CartItem cartItem : cartItemsByUser) {
            CartItemResponse cartItemResponse = new CartItemResponse();
            cartItemResponse.setCartId(cartItem.getId());
            cartItemResponse.setItemName(cartItem.getProductId());
            cartItemResponse.setQuantity(cartItem.getQuantity());
            cartItemResponse.setTotalPrice(cartItem.getPrice());
            cartItemResponseList.add(cartItemResponse);
        }

        return cartItemResponseList;
    }

    @Override
    public List<CartItem> getAllCartItemsByUser(String userId) {
//        return myUserRepository.findById(Long.valueOf(userId))
//                .map(cartItemRepository::findAllByUser)
//                .orElseGet(List::of);

        return  cartItemRepository.findAllByUserId(userId);
    }

    @Override
    public void clearCartByUser(String userId) {
//         myUserRepository.findById(Long.valueOf(userId))
//                 .ifPresent(cartItemRepository::deleteByUser);

        cartItemRepository.deleteByUserId(userId);
    }


}
