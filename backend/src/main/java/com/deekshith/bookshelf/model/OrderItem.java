package com.deekshith.bookshelf.model;

public class OrderItem {

    private String name;

    private Integer qty;

    private String image;

    private String productId;

    public OrderItem(String name, Integer qty, String image, String productId) {
        this.name = name;
        this.qty = qty;
        this.image = image;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
