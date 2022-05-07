package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;

// Interface for role related services
public interface RoleService {
    Role getRoleName(ERole name);
}
