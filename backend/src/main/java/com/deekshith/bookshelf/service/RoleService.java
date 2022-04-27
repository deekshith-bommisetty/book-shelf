package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;

public interface RoleService {
    Role getRoleName(ERole name);
}
