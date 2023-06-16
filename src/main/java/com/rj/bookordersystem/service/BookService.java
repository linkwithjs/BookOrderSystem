package com.rj.bookordersystem.service;

import com.rj.bookordersystem.exceptions.CustomException;
import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.models.User;
import com.rj.bookordersystem.repository.BookRepository;
import com.rj.bookordersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepository;

    // get all books
    public List<Book> getAllBooks() {
        List<Book> list = (List<Book>) this.bookRepo.findAll();
        return list;
    }

    // get single book by id
    public Book getBookById(int id) {
        Book book = null;
        try {
            book = this.bookRepo.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    // adding the book
    public Book add (Book b) {
        Book newBook = new Book();
        newBook.setBookName(b.getBookName());
        newBook.setPrice(b.getPrice());
        newBook.setPublisher(b.getPublisher());
        newBook.setQuantity(b.getQuantity());
        newBook.setUser(getAuthenticatedUser().get());
        return bookRepo.save(newBook);
    }

// Delete a book
    public void deleteBook(int bid) {
        bookRepo.deleteById(bid);
    }

    // Update book
    public Book updateBook(Book book, int bookId) {
        Book result = bookRepo.findById(bookId);
        try {
            if (result != null) {
                book.setId(bookId);
                bookRepo.save(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
