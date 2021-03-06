package com.deekshith.bookshelf.controller;

import com.deekshith.bookshelf.config.service.UserDetailsImpl;
import com.deekshith.bookshelf.model.Order;
import com.deekshith.bookshelf.model.OrderItem;
import com.deekshith.bookshelf.model.PaymentResult;
import com.deekshith.bookshelf.model.ShippingAddress;
import com.deekshith.bookshelf.model.builder.OrderBuilder;
import com.deekshith.bookshelf.payload.request.OrderRequest;
import com.deekshith.bookshelf.payload.response.MessageResponse;
import com.deekshith.bookshelf.payload.response.Response;
import com.deekshith.bookshelf.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// All endpoints concerning Order related functionality
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    // @desc    Update order to paid
    // @route   GET /api/orders/:id/pay
    // @access  Private/Admin
    @RequestMapping(value = "/api/orders/{id}/pay", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrderToPaid(@PathVariable("id") String id) {

        Order order = orderService.getOrder(id);
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(order != null){
            order.setPaid(true);
            order.setPaidAt(new Date());
            PaymentResult paymentResult = new PaymentResult(order.getUserId(), "success", order.getPaidAt(), userDetails.getEmail());
            order.setPaymentResult(paymentResult);
            order.setPaymentMethod("PayPal");
            return ResponseEntity.ok(orderService.saveOrder(order));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Order not found"));
        }
    }

    // @desc    Update order to delivered
    // @route   GET /api/orders/:id/deliver
    // @access  Private/Admin
    @RequestMapping(value = "/api/orders/{id}/deliver", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrderToDelivered(@PathVariable("id") String id) {

        Order order = orderService.getOrder(id);
        if(order != null){
            order.setDelivered(true);
            order.setDeliveredAt(new Date());

            return ResponseEntity.ok(orderService.saveOrder(order));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Order not found"));
        }
    }

    // @desc    Get all orders
    // @route   GET /api/orders
    // @access  Private/Admin
    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orderList = orderService.getOrders();
        Response data = new Response(orderList);
        if(!orderList.isEmpty()){
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to retrieve all orders"));
        }
    }

    // @desc    Get order by ID
    // @route   GET /api/orders/:id
    // @access  Private
    @RequestMapping(value = "/api/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrderById(@PathVariable("id") String id) {

        Order order = orderService.getOrder(id);
        if(order != null){
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Order not found"));
        }
    }

    // @desc    Get logged in user orders
    // @route   GET /api/orders/myorders
    // @access  Private
    @RequestMapping(value = "/api/orders/myorders", method = RequestMethod.GET)
    public ResponseEntity<?> getMyOrders() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> ordersList = orderService.getOrdersById(userDetails.getId());
        Response data = new Response(ordersList);
        if(ordersList != null){
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No Orders were found"));
        }
    }

    // @desc    Create new order
    // @route   POST /api/orders
    // @access  Private
    @RequestMapping(value = "/api/orders", method = RequestMethod.POST)
    public ResponseEntity<?> addOrderItems(@RequestBody OrderRequest orderRequest) {

        if (orderRequest.getOrderItems() == null || orderRequest.getOrderItems().size() == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No order items"));
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ShippingAddress shippingAddress = new ShippingAddress(orderRequest.getAddress(), orderRequest.getCity(), orderRequest.getPostalCode(), orderRequest.getCountry());
        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.setUserId(userDetails.getId()).setShippingAddress(shippingAddress).setPaymentMethod(orderRequest.getPaymentMethod()).setTaxPrice(orderRequest.getTaxPrice()).setShippingPrice(orderRequest.getShippingPrice()).setTaxPrice(orderRequest.getItemsPrice()).getOrder();
        ArrayList<OrderItem> OrderItemsList = new ArrayList<>();
        orderRequest.getOrderItems().forEach(orderItem -> {
            OrderItem newOrderItem = new OrderItem(orderItem.getName(), orderItem.getQty(), orderItem.getImage(), orderItem.getProductId());
            OrderItemsList.add(newOrderItem);
        });
        order.setOrderItems(OrderItemsList);
        return ResponseEntity.ok(orderService.saveOrder(order));
    }
}
