package com.deekshith.bookshelf.payload.request;

import com.deekshith.bookshelf.model.OrderItem;

import java.util.ArrayList;

public class OrderRequest {

    private ArrayList<OrderItem> orderItems;

    private String address;

    private  String city;

    private String postalCode;

    private String country;

    private String paymentMethod;

    private Double itemsPrice;

    private Double taxPrice;

    private Double shippingPrice;

    private Double totalPrice;

    public OrderRequest(ArrayList<OrderItem> orderItems, String address, String city, String postalCode, String country, String paymentMethod, Double itemsPrice, Double taxPrice, Double shippingPrice, Double totalPrice) {
        this.orderItems = orderItems;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.paymentMethod = paymentMethod;
        this.itemsPrice = itemsPrice;
        this.taxPrice = taxPrice;
        this.shippingPrice = shippingPrice;
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(Double itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
