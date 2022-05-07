package com.deekshith.bookshelf.model.builder;

import com.deekshith.bookshelf.model.Product;

// Product object builder which uses builder pattern
public class ProductBuilder {

    private String name;

    private String image;

    private String brand;

    private String category;

    private String description;

    private Double rating;

    private Integer numReviews;

    private Double price;

    private Integer countInStock;

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public ProductBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public ProductBuilder setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
        return this;
    }

    public ProductBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setCountInStock(Integer countInStock) {
        this.countInStock = countInStock;
        return this;
    }

    public Product getProducct(){
        return new Product(name, image, brand, category, description, rating, numReviews, price, countInStock);
    }
}
