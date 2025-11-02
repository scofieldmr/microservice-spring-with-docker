package com.ecomapp.service;

import com.ecomapp.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
