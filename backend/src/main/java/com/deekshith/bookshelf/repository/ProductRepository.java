package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Override
    Optional<Product> findById(String id);
}
