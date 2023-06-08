package com.rj.bookordersystem.service;

import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.models.User;
import com.rj.bookordersystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;

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
        newBook.setUser(getAuthenticatedUser());
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

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

}
