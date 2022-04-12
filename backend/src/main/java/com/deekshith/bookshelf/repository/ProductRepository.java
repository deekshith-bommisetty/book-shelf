package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Override
    Optional<Product> findById(String s);
}
