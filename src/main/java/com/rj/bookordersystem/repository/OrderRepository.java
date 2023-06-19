package com.rj.bookordersystem.repository;

import com.rj.bookordersystem.models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Integer>{
    public Order findById(int id);
    @Query(value="SELECT u.username, o.date_attribute, o.status, b.book_name, b.quantity, b.price, b.publisher FROM orders o\n" +
        "JOIN users u on u.id= o.user_id\n" +
        "JOIN orders_books ob on o.id = ob.order_id\n" +
        "JOIN books b on ob.books_book_id = b.book_id WHERE o.status='PENDING'", nativeQuery = true)
    List<Order> pendingOrders();

}
