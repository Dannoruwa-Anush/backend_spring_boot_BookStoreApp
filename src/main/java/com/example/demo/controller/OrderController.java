package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order") //Servers entire controller under /order URI
public class OrderController {
    
    @Autowired
    OrderService orderService;

     @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }
    //---

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id){
        try {
            //Try to find order by id
            Order order = orderService.getOrderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(order);

        } catch (NoSuchElementException e) {
            //If there is no order by id, throw NoSuchElementException from OrderServiceIml.java
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    //---

    @PostMapping
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
          try {
            //Try to find order by id
            Order orderControll = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderControll);

        } catch (NoSuchElementException e) {
            //If there is no order by id, throw NoSuchElementException from OrdererviceIml.java
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    //---

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable long id, @RequestBody Order order){
        try {
            Order updateOrder = orderService.updateOrder(id, order);
            return ResponseEntity.status(HttpStatus.OK).body(updateOrder);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    //---

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    //---
}
