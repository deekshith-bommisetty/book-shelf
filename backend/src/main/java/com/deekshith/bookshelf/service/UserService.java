package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.User;
import com.deekshith.bookshelf.payload.request.ProfileRequest;
import com.deekshith.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User updateUser(String id, ProfileRequest profileRequest){

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User currentUser = user.get();
            if(profileRequest.getName() != null){
                currentUser.setName(profileRequest.getName());
            }
            if(profileRequest.getEmail() != null){
                currentUser.setEmail(profileRequest.getEmail());
            }
            if(profileRequest.getPassword() != null){
                currentUser.setPassword(profileRequest.getPassword());
            }
            return userRepository.save(currentUser);
        }
        return null;
    }

    public List <User> retrieveALlUsers(){
        return userRepository.findAll();
    }

    public  User retrieveUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User userResult = user.get();
            return userResult;
        }
        return null;
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

}
