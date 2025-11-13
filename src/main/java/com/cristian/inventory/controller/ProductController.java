package com.cristian.inventory.controller;

import com.cristian.inventory.dto.ProductDto;
import com.cristian.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto saved = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
