package com.rj.bookordersystem.controller;

import com.rj.bookordersystem.dto.OrderDTO;
import com.rj.bookordersystem.dto.ResponseDTO;
import com.rj.bookordersystem.models.Order;
import com.rj.bookordersystem.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Data.";
    }

    // Fetch All Orders
    @GetMapping("/get-orders")
    public ResponseEntity<?> getOrders() {
        List<Order> list = orderService.getAllOrders();
        return ResponseDTO.successResponse("", list);
    }

//    get pending orders
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getPendingOrders() {
        List<Order> list = orderService.getPendingOrders();
        return ResponseDTO.successResponse("", list);
    }

//    Create order
    @PostMapping("/add-order")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDTO order) {
        return orderService.addOrder(order);
    }

}
