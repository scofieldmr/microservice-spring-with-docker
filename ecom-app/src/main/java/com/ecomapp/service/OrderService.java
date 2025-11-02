package com.ecomapp.service;

import com.ecomapp.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(String userId);
}
