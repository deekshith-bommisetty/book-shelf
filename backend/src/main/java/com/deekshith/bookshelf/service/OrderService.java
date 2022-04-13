package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.Order;
import com.deekshith.bookshelf.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List <Order> retrieveAllOrders(){
        return orderRepository.findAll();
    }

    public Order retrieveOrder(String id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order fetchedOrder = order.get();
            return fetchedOrder;
        }
        return null;
    }

    public List<Order> retrieveOrders(String userId){

        List<Order> ordersList = orderRepository.findByUser(userId);
        System.out.println("Inside service");
        System.out.println(ordersList);
        if (ordersList != null) {
            return ordersList;
        }
        return null;
    }

}
