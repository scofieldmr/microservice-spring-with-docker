package com.ms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long orderId;
    private BigDecimal totalPrice;
    private String orderStatus;
    private List<OrderItemDto> orderItems;
    private LocalDateTime createdAt;
}
