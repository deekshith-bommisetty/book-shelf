package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
