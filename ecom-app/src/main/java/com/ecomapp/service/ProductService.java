package com.ecomapp.service;

import com.ecomapp.dto.ProductCreateRequest;
import com.ecomapp.dto.ProductResponse;
import com.ecomapp.dto.ProductUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponse createNewProduct(ProductCreateRequest newProduct);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getAllAvailableProducts();

    Optional<ProductResponse> getProductById(long id);

    ProductResponse updateProductDetails(long id, ProductUpdateRequest updateProduct);

    boolean deleteProductById(long id);

    List<ProductResponse> searchProducts(String keyword);
}
