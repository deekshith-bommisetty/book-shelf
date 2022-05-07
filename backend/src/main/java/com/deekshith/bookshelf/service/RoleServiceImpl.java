package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import com.deekshith.bookshelf.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Custom implementation of the RoleService interface with adapter pattern
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role getRoleName(ERole name) {
        Optional<Role> fetchedRole = roleRepository.findByName(name);
        if (fetchedRole.isPresent()) {
            Role role = fetchedRole.get();
            return role;
        }
        return null;
    }
}
