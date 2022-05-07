package com.deekshith.bookshelf.payload.response;

import java.util.List;

// Response object for profile information
public class profileResponse {

    private String id;
    private String name;
    private String email;
    private List<String> roles;
    private String refreshToken;

    public profileResponse(String id, String name, String email, List<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public profileResponse(String id, String name, String email, List<String> roles, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
