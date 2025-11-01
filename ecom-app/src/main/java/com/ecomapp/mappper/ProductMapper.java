package com.ecomapp.mappper;

import com.ecomapp.dto.ProductCreateRequest;
import com.ecomapp.dto.ProductResponse;
import com.ecomapp.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse productToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategory(product.getCategory());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.isActive());

        return productResponse;
    }

    public Product productRequestToProduct(ProductCreateRequest productCreateRequest) {
        Product product = new Product();

        product.setName(productCreateRequest.getName());
        product.setDescription(productCreateRequest.getDescription());
        product.setPrice(productCreateRequest.getPrice());
        product.setStockQuantity(productCreateRequest.getStockQuantity());
        product.setCategory(productCreateRequest.getCategory());
        product.setImageUrl(productCreateRequest.getImageUrl());

        return product;
    }
}
