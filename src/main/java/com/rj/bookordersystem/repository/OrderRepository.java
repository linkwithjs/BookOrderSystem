package com.rj.bookordersystem.repository;

import com.rj.bookordersystem.models.Order;
import org.springframework.data.repository.CrudRepository;
public interface OrderRepository extends CrudRepository<Order,Integer>{
    public Order findById(int id);
}
