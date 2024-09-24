package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Order is not found" + id));
    }

    @Override
    public Order createOrder(Order order) {
     return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(long id, Order order) {

        //get existing Order
        Order existingOrder = getOrderById(id);
        existingOrder.setStatus(order.getStatus());
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
