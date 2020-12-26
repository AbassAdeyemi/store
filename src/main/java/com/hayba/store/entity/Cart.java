package com.hayba.store.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter @Setter
public class Cart {
    @Id
    private String id;
    @DBRef
    private User user;
   private List<Product> products = new ArrayList<>();

}
