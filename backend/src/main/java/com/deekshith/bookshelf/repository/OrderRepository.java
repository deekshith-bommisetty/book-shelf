package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    @Query("{userId :?0}")
    List<Order> findByUser(String userId);

    @Override
    Optional<Order> findById(String s);

}
