package com.ms.orderservice.client.product;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {

    @GetExchange("/api/v1/product/get/{product_id}")
    ProductResponse getProductById(@PathVariable("product_id") long product_id);
}
