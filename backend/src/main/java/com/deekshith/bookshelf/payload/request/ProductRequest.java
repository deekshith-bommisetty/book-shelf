package com.deekshith.bookshelf.payload.request;

// Request Object for Product
public class ProductRequest {

    private String name;

    private String image;

    private String brand;

    private String category;

    private String description;

    private Double rating;

    private Integer numReviews;

    private Double price;

    private Integer countInStock;

    public ProductRequest(String name, String image, String brand, String category, String description, Double rating, Integer numReviews, Double price, Integer countInStock) {
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
}
