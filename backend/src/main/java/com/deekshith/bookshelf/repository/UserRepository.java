package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{name :?0}")
    Optional<User> findByUsername(String username);

    @Query("{email :?0}")
    Optional<User> findByEmail(String email);

    @Query("{email :?0}")
    Boolean existsByEmail(String email);

    @Query("{id :?0}")
    Optional<User> findByUserId(String id);

    @Override
    <S extends User> List<S> findAll(Example<S> example);
}


