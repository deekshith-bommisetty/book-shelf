package com.deekshith.bookshelf.model.builder;

import com.deekshith.bookshelf.model.User;

// User object builder which uses builder pattern
public class UserBuilder {

    private String name;

    private String email;

    private String password;

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public User getUser(){
        return new User(name, email, password);
    }

}
