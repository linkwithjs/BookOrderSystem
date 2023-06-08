package com.rj.bookordersystem.controller;

import com.rj.bookordersystem.models.Order;
import com.rj.bookordersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

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

    @PostMapping("/add-order")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order b = null;
        try {
            b = this.orderService.addOrder(order);
            return ResponseEntity.of(Optional.of(b));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
