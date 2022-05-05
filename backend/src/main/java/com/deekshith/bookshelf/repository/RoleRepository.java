package com.deekshith.bookshelf.repository;

import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(ERole name);
}
