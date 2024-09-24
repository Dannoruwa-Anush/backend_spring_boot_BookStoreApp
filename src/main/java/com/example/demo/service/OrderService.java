package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;

@Service
public interface OrderService {
    //interface methods are implicitly abstract and public
    List<Order> getAllOrders(); //List-> import java.util.List;
    Order getOrderById(long id);
    Order createOrder(Order order);
    Order updateOrder(long id, Order order);
    void deleteOrder(long id);
}
