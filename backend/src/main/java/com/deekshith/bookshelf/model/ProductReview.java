package com.deekshith.bookshelf.model;

import javax.validation.constraints.NotBlank;

// Composition based ProductReview in the Product Model
public class ProductReview {
    @NotBlank
    private String name;

    @NotBlank
    private Double rating;

    @NotBlank
    private String comment;

    private String id;

    public ProductReview(String name, Double rating, String comment, String id) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
