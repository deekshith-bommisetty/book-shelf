package com.deekshith.bookshelf.controller;

import com.deekshith.bookshelf.config.service.UserDetailsImpl;
import com.deekshith.bookshelf.model.Order;
import com.deekshith.bookshelf.model.OrderItem;
import com.deekshith.bookshelf.model.ShippingAddress;
import com.deekshith.bookshelf.payload.request.OrderRequest;
import com.deekshith.bookshelf.payload.response.MessageResponse;
import com.deekshith.bookshelf.repository.OrderRepository;
import com.deekshith.bookshelf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    // Upate order to paid API is pending

    @RequestMapping(value = "/api/orders/{id}/deliver", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrderToDelivered(@PathVariable("id") String id) {

        Order order = orderService.retrieveOrder(id);
        if(order != null){
            order.setDelivered(true);
            order.setDeliveredAt(new Date());

            return ResponseEntity.ok(orderRepository.save(order));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Order not found"));
        }
    }

    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrders() {
        // Check for admin role later
        List<Order> orderList = orderService.retrieveAllOrders();
        if(!orderList.isEmpty()){
            return ResponseEntity.ok(orderList);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to retrieve all orders"));
        }
    }

    @RequestMapping(value = "/api/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductById(@PathVariable("id") String id) {

        Order order = orderService.retrieveOrder(id);
        if(order != null){
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Order not found"));
        }
    }

    @RequestMapping(value = "/api/orders/myorders", method = RequestMethod.GET)
    public ResponseEntity<?> getMyOrders() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> ordersList = orderService.retrieveOrders(userDetails.getId().toString());
        System.out.println("Retrieved Orders List");
        System.out.println(ordersList);
        if(ordersList != null){
            return ResponseEntity.ok(ordersList);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No Orders were found"));
        }
    }

    @RequestMapping(value = "/api/orders", method = RequestMethod.POST)
    public ResponseEntity<?> addOrderItems(@RequestBody OrderRequest orderRequest) {

        if (orderRequest.getOrderItems() == null || orderRequest.getOrderItems().size() == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No order items"));
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ShippingAddress shippingAddress = new ShippingAddress(orderRequest.getAddress(), orderRequest.getCity(), orderRequest.getPostalCode(), orderRequest.getCountry());
        Order order = new Order(userDetails.getId().toString(), shippingAddress, orderRequest.getPaymentMethod(), orderRequest.getTaxPrice(), orderRequest.getShippingPrice(), orderRequest.getItemsPrice());
        ArrayList<OrderItem> OrderItemsList = new ArrayList<OrderItem>();
        orderRequest.getOrderItems().forEach(orderItem -> {
            OrderItem newOrderItem = new OrderItem(orderItem.getName(), orderItem.getQty(), orderItem.getImage(), orderItem.getProductId());
            OrderItemsList.add(newOrderItem);
        });
        order.setOrderItems(OrderItemsList);
        return ResponseEntity.ok(orderRepository.save(order));
    }
}
