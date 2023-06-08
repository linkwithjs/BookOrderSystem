package com.rj.bookordersystem.service;

import com.rj.bookordersystem.models.Order;
import com.rj.bookordersystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    // get all orders
    public List<Order> getAllOrders() {
        List<Order> list = (List<Order>) this.orderRepo.findAll();
        return list;
    }

    // adding the order book
    public Order addOrder(Order order) {
        Order result = orderRepo.save(order);
        return result;
    }

}
