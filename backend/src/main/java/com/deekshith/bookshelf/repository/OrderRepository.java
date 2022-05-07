package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Order Repository for performing DB operations on Orders Collections
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    @Query("{userId :?0}")
    List<Order> findByUser(String userId);

    Optional<Order> findById(String s);

}
