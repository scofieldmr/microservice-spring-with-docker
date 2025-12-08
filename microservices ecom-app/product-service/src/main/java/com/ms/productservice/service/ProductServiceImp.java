package com.ms.productservice.service;


import com.ms.productservice.dto.ProductCreateRequest;
import com.ms.productservice.dto.ProductResponse;
import com.ms.productservice.dto.ProductUpdateRequest;
import com.ms.productservice.entity.Product;
import com.ms.productservice.exception.ProductAlreadyExistsException;
import com.ms.productservice.exception.ProductNotFoundException;
import com.ms.productservice.mapper.ProductMapper;
import com.ms.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImp(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllAvailableProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse createNewProduct(ProductCreateRequest newProduct) {
        if(productRepository.existsByName(newProduct.getName())){
            throw new ProductAlreadyExistsException("Product already exists with name " + newProduct.getName());
        }
        Product createProduct = productMapper.productRequestToProduct(newProduct);
        Product savedProduct = productRepository.save(createProduct);
        return productMapper.productToProductResponse(savedProduct);
    }


    @Override
    public Optional<ProductResponse> getProductById(long id) {
        Product findProduct = productRepository.findById(id).orElse(null);
        if (findProduct == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(productMapper.productToProductResponse(findProduct));
    }

    @Override
    public ProductResponse updateProductDetails(long id, ProductUpdateRequest updateProduct) {
        Product findProduct = productRepository.findById(id).orElse(null);
        if (findProduct == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        findProduct.setName(updateProduct.getName());
        findProduct.setDescription(updateProduct.getDescription());
        findProduct.setPrice(updateProduct.getPrice());
        findProduct.setCategory(updateProduct.getCategory());
        findProduct.setStockQuantity(updateProduct.getStockQuantity());
        findProduct.setImageUrl(updateProduct.getImageUrl());

        Product updatedProduct = productRepository.save(findProduct);

        return productMapper.productToProductResponse(updatedProduct);
    }

    @Override
    public boolean deleteProductById(long id) {
        Product findProduct = productRepository.findById(id).orElse(null);
        if (findProduct != null) {
            findProduct.setActive(false);
            productRepository.save(findProduct);
            return true;
        }
        return false;
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        List<Product> productList = productRepository.findProductsByKeyword(keyword);
        return productList.stream()
                .map(productMapper::productToProductResponse).collect(Collectors.toList());
    }


}
