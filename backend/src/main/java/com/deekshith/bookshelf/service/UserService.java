package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import com.deekshith.bookshelf.model.User;
import com.deekshith.bookshelf.payload.request.ProfileRequest;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(ProfileRequest profileRequest);
    User getUser(String id);
    User getUserByEmail(String email);
    List<User> getUsers();
    void deleteUser(String id);
    Role getRoleByName(ERole eRole);
}
