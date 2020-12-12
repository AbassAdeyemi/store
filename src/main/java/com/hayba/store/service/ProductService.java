package com.hayba.store.service;

import com.hayba.store.entity.Product;
import com.hayba.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product add(Product product) {
        if(productRepository.findByName(product.getName()).isPresent())
            throw new IllegalArgumentException("Product already exists with name: " + product.getName());
        return productRepository.save(product);
    }

    public Product edit(String id, Product product) {
        return productRepository.findById(id)
                .map(p -> {
                    p.setPrice(product.getPrice());
                    p.setRatings(product.getRatings());
                    return p;
                }).orElseThrow(() -> new IllegalStateException("Product does not exist with id: "+id));
    }

    public void delete(String id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> new IllegalStateException("Product does not exist with id: "+id));
    }


}
