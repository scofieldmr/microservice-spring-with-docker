package com.ecomapp.controller;

import com.ecomapp.dto.OrderResponse;
import com.ecomapp.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> createOrder(@RequestHeader("X-User-ID") String userId) {
        OrderResponse orderResponse = orderService.createOrder(userId);
        return ResponseEntity.ok(orderResponse);
    }
}
