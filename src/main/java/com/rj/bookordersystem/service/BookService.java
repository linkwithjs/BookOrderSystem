package com.rj.bookordersystem.service;

import com.rj.bookordersystem.dto.ResponseDTO;
import com.rj.bookordersystem.exceptions.CustomException;
import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.models.User;
import com.rj.bookordersystem.repository.BookRepository;
import com.rj.bookordersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // get all available books
    public List<Book> getAllAvailableBooks() {
        List<Book> list = (List<Book>) this.bookRepo.findBooksByQuantityGreaterThanZero();
        return list;
    }

    // get single book by id
    public ResponseEntity<?> getBookById(int id) {
        Book book = bookRepo.findById(id);
        if(book!=null){
            return ResponseDTO.successResponse("",book);
        }else{
            return ResponseDTO.successResponse("Book with id: "+id+" not found.");
        }
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
    public ResponseEntity<?> deleteBook(int bid) {
        Book book = bookRepo.findById(bid);
        if(book!=null){
            bookRepo.deleteById(bid);
            return ResponseDTO.successResponse("Book Deleted Successfully!");
        }else{
            throw new CustomException("Error: Book not found for this id : " + bid);
        }
    }

    // Update book
    public ResponseEntity<?> updateBook(Book book, int bookId) {
        Book result = bookRepo.findById(bookId);
        if(result !=null){
            book.setId(bookId);
            bookRepo.save(book);
            return ResponseDTO.successResponse("Book Updated Successfully.",result);
        }else{
            return ResponseDTO.successResponse("Book Couldn't be updated!");
        }
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
