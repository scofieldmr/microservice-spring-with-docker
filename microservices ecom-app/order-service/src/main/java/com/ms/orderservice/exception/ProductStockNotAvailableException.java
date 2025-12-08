package com.ms.orderservice.exception;

public class ProductStockNotAvailableException extends RuntimeException {
    public ProductStockNotAvailableException(String message) {
        super(message);
    }
}
