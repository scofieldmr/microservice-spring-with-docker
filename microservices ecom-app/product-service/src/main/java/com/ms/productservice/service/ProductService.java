package com.ms.productservice.service;



import com.ms.productservice.dto.ProductCreateRequest;
import com.ms.productservice.dto.ProductResponse;
import com.ms.productservice.dto.ProductUpdateRequest;

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
