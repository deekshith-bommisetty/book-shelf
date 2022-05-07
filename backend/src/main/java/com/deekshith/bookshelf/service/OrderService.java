package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.Order;

import java.util.List;

// Interface for order related services
public interface OrderService {

    Order saveOrder(Order order);
    List<Order> getOrders();
    Order getOrder(String id);
    List<Order> getOrdersById(String userId);
}
