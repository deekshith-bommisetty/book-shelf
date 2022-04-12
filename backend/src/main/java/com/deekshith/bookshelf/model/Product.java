package com.deekshith.bookshelf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Document(collection = "products")
public class Product {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotBlank
    private String brand;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NotBlank
    private Double rating;

    @NotBlank
    private Integer numReviews;

    @NotBlank
    private Double price;

    @NotBlank
    private Integer countInStock;

    private String user;

    private ArrayList<ProductReview> reviews = new ArrayList<>();

    public Product(String name, String image, String brand, String category, String description, Double rating, Integer numReviews, Double price, Integer countInStock) {
        this.name = name;
        this.image = image;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.rating = rating;
        this.numReviews = numReviews;
        this.price = price;
        this.countInStock = countInStock;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCountInStock() {
        return countInStock;
    }

    public void setCountInStock(Integer countInStock) {
        this.countInStock = countInStock;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<ProductReview> getReviews() {
        return reviews;
    }

    public void setReviews(ProductReview review) {
        this.reviews.add(review);
    }
}
