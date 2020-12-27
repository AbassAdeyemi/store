package com.hayba.store.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Document
@Getter @Setter
public class User {
    @Id
    private String id;
    @Email
    @Indexed(unique = true)
    private String username;
    @Size(min = 8)
    private String password;
    private String role = "ROLE_USER";

    public User(@Email String username, @Size(min = 8) String password) {
        this.username = username;
        this.password = password;
    }
}
