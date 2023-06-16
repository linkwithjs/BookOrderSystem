package com.rj.bookordersystem.service;

import com.rj.bookordersystem.exceptions.CustomException;
import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.models.Order;
import com.rj.bookordersystem.models.User;
import com.rj.bookordersystem.repository.BookRepository;
import com.rj.bookordersystem.repository.OrderRepository;
import com.rj.bookordersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    // get all orders
    public List<Order> getAllOrders() {
        List<Order> list = (List<Order>) this.orderRepo.findAll();
        return list;
    }

    // adding the order book
    public Order addOrder(Order order) {
        List<Book> booklist;
        order.getBooks().stream().map(book -> {
            System.out.println(book);
            return null;
        });
        order.setUser(getAuthenticatedUser().get());
        Order result = orderRepo.save(order);
        return result;
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        String finalUsername = username;
        return Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Error: " + finalUsername + " is not found.")));
    }

}
