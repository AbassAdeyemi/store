package com.hayba.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Getter @Setter  @NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private long ratings;

    public Product(String name, BigDecimal price, long ratings) {
        this.name = name;
        this.price = price;
        this.ratings = ratings;
    }
}
