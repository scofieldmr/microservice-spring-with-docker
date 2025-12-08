package com.ms.productservice.controller;


import com.ms.productservice.dto.ProductCreateRequest;
import com.ms.productservice.dto.ProductResponse;
import com.ms.productservice.dto.ProductUpdateRequest;
import com.ms.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<ProductResponse> productResponseList = productService.getAllProducts();
        if (productResponseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNewProduct(@RequestBody ProductCreateRequest productCreateRequest){
        ProductResponse newProductResponse = productService.createNewProduct(productCreateRequest);
        return new ResponseEntity<>(newProductResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get/{product_id}")
    public ResponseEntity<?> getProductById(@PathVariable("product_id") long product_id){
        return productService.getProductById(product_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity<?> updateProductDetails(@PathVariable("product_id") long product_id,
                                                  @RequestBody ProductUpdateRequest productUpdateRequest){
        ProductResponse updatedProductResponse = productService.updateProductDetails(product_id, productUpdateRequest);
        return updatedProductResponse !=null ?
                ResponseEntity.ok(updatedProductResponse) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("product_id") long product_id){
        return productService.deleteProductById(product_id) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getActiveProducts")
    public ResponseEntity<?> getActiveProducts(){
        List<ProductResponse> activeProductList = productService.getAllAvailableProducts();
        if (activeProductList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activeProductList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getProductsByKeyword(@RequestParam String keyword){
        List<ProductResponse> productListByKeyword = productService.searchProducts(keyword);
        if (productListByKeyword.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productListByKeyword, HttpStatus.OK);
    }

}
