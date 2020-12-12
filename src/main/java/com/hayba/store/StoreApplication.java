package com.hayba.store;

import com.hayba.store.entity.Product;
import com.hayba.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
public class StoreApplication {

	private final ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

//	@PostConstruct
//	public void load() {
//		repository.save(new Product("Apple", BigDecimal.valueOf(10), 1));
//	}

}
