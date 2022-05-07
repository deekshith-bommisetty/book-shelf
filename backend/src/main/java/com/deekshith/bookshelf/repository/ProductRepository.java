package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Product Repository for performing DB operations on Products Collections
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findById(String id);
}
