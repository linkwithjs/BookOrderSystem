package com.rj.bookordersystem.service;

import com.rj.bookordersystem.dto.OrderDTO;
import com.rj.bookordersystem.dto.OrderResponseDTO;
import com.rj.bookordersystem.dto.ResponseDTO;
import com.rj.bookordersystem.exceptions.CustomException;
import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.models.Order;
import com.rj.bookordersystem.models.User;
import com.rj.bookordersystem.repository.BookRepository;
import com.rj.bookordersystem.repository.OrderRepository;
import com.rj.bookordersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ResponseEntity<?> addOrder(OrderDTO orderDTO) {
        ArrayList<Book> books = new ArrayList<>();
        Order order = new Order();
        orderDTO.getBooks().forEach(book -> {
            Book bookDetails = bookRepository.findById(book.getId());
            books.add(bookDetails);
        });

        order.setStatus(orderDTO.getStatus());
        order.setDateAttribute(orderDTO.getDateAttribute());
        order.setBooks(books);
        order.setUser(getAuthenticatedUser().get());
        Order savedOrder = orderRepo.save(order);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(savedOrder.getId());
        orderResponseDTO.setDateAttribute(savedOrder.getDateAttribute());
        orderResponseDTO.setStatus(savedOrder.getStatus());
        orderResponseDTO.setUser(getAuthenticatedUser().get().getUsername());
        orderResponseDTO.setBooks(savedOrder.getBooks());


        return ResponseDTO.successResponse("Order placed successfully", orderResponseDTO);
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
