package com.deekshith.bookshelf.model.builder;

import com.deekshith.bookshelf.model.Order;
import com.deekshith.bookshelf.model.OrderItem;
import com.deekshith.bookshelf.model.ShippingAddress;

import java.util.ArrayList;

// Order object builder which uses builder pattern
public class OrderBuilder {
    private  String userId;

    private ArrayList<OrderItem> orderItems;

    private ShippingAddress shippingAddress;

    private String paymentMethod;

    private Double taxPrice;

    private Double shippingPrice;

    private Double totalPrice;

    public OrderBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public OrderBuilder setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public OrderBuilder setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderBuilder setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
        return this;
    }

    public OrderBuilder setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
        return this;
    }

    public OrderBuilder setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Order getOrder(){
        return new Order(userId, shippingAddress, paymentMethod, taxPrice, shippingPrice, totalPrice);
    }
}
