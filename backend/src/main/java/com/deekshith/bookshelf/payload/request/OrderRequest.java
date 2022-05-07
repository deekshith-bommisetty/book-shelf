package com.deekshith.bookshelf.payload.request;

import com.deekshith.bookshelf.model.OrderItem;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

// Request Object for Order
public class OrderRequest {

    private ArrayList<OrderItem> orderItems;

    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotBlank(message = "city is mandatory")
    private  String city;
    @NotBlank(message = "postalCode is mandatory")
    private String postalCode;
    @NotBlank(message = "country is mandatory")
    private String country;
    @NotBlank(message = "paymentMethod is mandatory")
    private String paymentMethod;
    @NotBlank(message = "itemsPrice is mandatory")
    private Double itemsPrice;

    private Double taxPrice;

    private Double shippingPrice;
    @NotBlank(message = "totalPrice is mandatory")
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
