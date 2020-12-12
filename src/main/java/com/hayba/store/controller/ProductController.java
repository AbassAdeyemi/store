package com.hayba.store.controller;

import com.hayba.store.entity.Product;
import com.hayba.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<Product> getAll(Pageable pageable) {
        return productService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.add(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> edit(@PathVariable String id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.edit(id, product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable  String id) {
        productService.delete(id);
    }

}
