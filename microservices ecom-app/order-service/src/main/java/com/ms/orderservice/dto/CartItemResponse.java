package com.ms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long cartId;
    private String itemName;
    private Integer quantity;
    private BigDecimal totalPrice;
}
