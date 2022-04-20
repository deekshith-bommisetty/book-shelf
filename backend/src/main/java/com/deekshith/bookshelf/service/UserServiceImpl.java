package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.config.service.UserDetailsImpl;
import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import com.deekshith.bookshelf.model.User;
import com.deekshith.bookshelf.payload.request.ProfileRequest;
import com.deekshith.bookshelf.repository.RoleRepository;
import com.deekshith.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public  UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(ProfileRequest profileRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> retrievedUser = userRepository.findById(userDetails.getId().toString());
        if (retrievedUser.isPresent()) {
            User user = retrievedUser.get();
            if(profileRequest.getName() != null){
                user.setName(profileRequest.getName());
            } else {
                user.setName(userDetails.getUsername());
            }
            if(profileRequest.getEmail() != null){
                user.setEmail(profileRequest.getEmail());
            } else {
                user.setEmail(userDetails.getEmail());
            }
            if(profileRequest.getPassword() != null){
                user.setPassword(encoder.encode(profileRequest.getPassword()));
            } else {
                user.setPassword(userDetails.getPassword());
            }
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User getUser(String id) {
        Optional<User> retrievedUser = userRepository.findById(id);
        if (retrievedUser.isPresent()) {
            User user = retrievedUser.get();
            return user;
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> retrievedUser = userRepository.findByEmail(email);
        if (retrievedUser.isPresent()) {
            User user = retrievedUser.get();
            return user;
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> retrievedUsers = userRepository.findAll();
        if (!retrievedUsers.isEmpty()) {
            return  retrievedUsers;
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {
         userRepository.deleteById(id);
    }

    @Override
    public Role getRoleByName(ERole eRole) {
        Optional<Role> retrievedRole = roleRepository.findByName(ERole.ROLE_USER);
        if (retrievedRole.isPresent()) {
            Role role = retrievedRole.get();
            return role;
        }
        return null;
    }
}
