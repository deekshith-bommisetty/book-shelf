package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.Order;
import com.deekshith.bookshelf.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Custom implementation of the OrderService interface with adapter pattern
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order fetchedOrder = order.get();
            return fetchedOrder;
        }
        return null;
    }

    @Override
    public List<Order> getOrdersById(String userId) {
        List<Order> ordersList = orderRepository.findByUser(userId);
        if (ordersList != null) {
            return ordersList;
        }
        return null;
    }
}





