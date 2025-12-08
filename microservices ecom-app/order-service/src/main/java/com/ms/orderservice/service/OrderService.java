package com.ms.orderservice.service;


import com.ms.orderservice.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(String userId);

}
