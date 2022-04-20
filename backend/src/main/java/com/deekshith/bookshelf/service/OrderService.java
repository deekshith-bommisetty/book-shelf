package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.Order;

import java.util.List;

public interface OrderService {

    Order saveOrder(Order order);
    List<Order> getOrders();
    Order getOrder(String id);
    List<Order> getOrdersById(String userId);
}
