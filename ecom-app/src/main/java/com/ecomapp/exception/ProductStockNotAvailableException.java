package com.ecomapp.exception;

public class ProductStockNotAvailableException extends RuntimeException {
    public ProductStockNotAvailableException(String message) {
        super(message);
    }
}
