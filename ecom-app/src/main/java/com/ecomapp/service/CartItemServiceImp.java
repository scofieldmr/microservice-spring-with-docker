package com.ecomapp.service;

import com.ecomapp.Repository.CartItemRepository;
import com.ecomapp.Repository.MyUserRepository;
import com.ecomapp.Repository.ProductRepository;
import com.ecomapp.dto.CartItemRequest;
import com.ecomapp.dto.CartItemResponse;
import com.ecomapp.entity.CartItem;
import com.ecomapp.entity.MyUsers;
import com.ecomapp.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemServiceImp implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final MyUserRepository myUserRepository;

    public CartItemServiceImp(CartItemRepository cartItemRepository, ProductRepository productRepository, MyUserRepository myUserRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.myUserRepository = myUserRepository;
    }

    @Override
    public CartItemResponse addItemToCart(String userId,
                                          CartItemRequest cartItemRequest) {

        MyUsers findUser = myUserRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User Id not found" + userId));

        Product product = productRepository
                .findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product Id not found" + cartItemRequest.getProductId()));

        if(product.getStockQuantity()<cartItemRequest.getQuantity()){
            throw new RuntimeException("Product Stock Quantity not available for requested item.");
        }

        BigDecimal totalPrice = BigDecimal.valueOf(0);

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(findUser, product);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+cartItemRequest.getQuantity());
            totalPrice = product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity()));
            existingCartItem.setPrice(totalPrice);
            cartItemRepository.save(existingCartItem);

            return new CartItemResponse(existingCartItem.getId(),existingCartItem.getProduct().getName(),
                     existingCartItem.getQuantity(),existingCartItem.getPrice());
        }

        CartItem newItem = new CartItem();
        newItem.setUser(findUser);
        newItem.setProduct(product);
        newItem.setQuantity(cartItemRequest.getQuantity());

        totalPrice = product.getPrice().multiply(new BigDecimal(cartItemRequest.getQuantity()));
        newItem.setPrice(totalPrice);

        CartItem addedItem = cartItemRepository.save(newItem);

        return new CartItemResponse(addedItem.getId(),addedItem.getProduct().getName(),addedItem.getQuantity(),addedItem.getPrice());
    }

    @Transactional
    @Override
    public boolean deleteItemFromCart(String userId, Long productId) {

        MyUsers findUser = myUserRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User Id not found" + userId));

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Id not found" + productId));

        if(findUser!=null && product!=null){
            cartItemRepository.deleteByUserAndProduct(findUser,product);
            return true;
        }

        return false;
    }

    @Override
    public List<CartItemResponse> getCartItems(String userId) {

        MyUsers findUser = myUserRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User Id not found" + userId));

        List<CartItem> cartItemsByUser = cartItemRepository.findAllByUser(findUser);

        List<CartItemResponse> cartItemResponseList = new ArrayList<>();

        for (CartItem cartItem : cartItemsByUser) {
            CartItemResponse cartItemResponse = new CartItemResponse();
            cartItemResponse.setCartId(cartItem.getId());
            cartItemResponse.setItemName(cartItem.getProduct().getName());
            cartItemResponse.setQuantity(cartItem.getQuantity());
            cartItemResponse.setTotalPrice(cartItem.getPrice());
            cartItemResponseList.add(cartItemResponse);
        }

        return cartItemResponseList;
    }

    @Override
    public List<CartItem> getAllCartItemsByUser(String userId) {
        return myUserRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findAllByUser)
                .orElseGet(List::of);
    }

    @Override
    public void clearCartByUser(String userId) {
         myUserRepository.findById(Long.valueOf(userId))
                 .ifPresent(cartItemRepository::deleteByUser);
    }


}
