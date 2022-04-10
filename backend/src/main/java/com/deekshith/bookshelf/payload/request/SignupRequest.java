package com.deekshith.bookshelf.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SignupRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    private String password;

    public SignupRequest(String name, String email, Set<String> roles, String password) {
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRole(Set<String> roles) {
        this.roles = roles;
    }
}
