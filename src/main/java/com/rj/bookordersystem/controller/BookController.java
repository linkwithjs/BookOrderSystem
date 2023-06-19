package com.rj.bookordersystem.controller;

import com.rj.bookordersystem.dto.ResponseDTO;
import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Data.";
    }

    // Fetch All Books
    @GetMapping("/get-books")
    public ResponseEntity<?> getBooks() {
        List<Book> list = bookService.getAllBooks();
        return ResponseDTO.successResponse("", list);
    }

    //Get All Available Books
    @GetMapping("/books/available")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getAvailableBook() {
        List<Book> list = bookService.getAllAvailableBooks();
        return ResponseDTO.successResponse("",list);
    }

    @PostMapping("/add-book")
    @PreAuthorize("hasAuthority('USER')")
    public Book  addBook(@RequestBody Book book) {return bookService.add(book);}

    // Fetch Single book
    @GetMapping("/books/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getBook(@Valid @PathVariable("id") int id) {return bookService.getBookById(id);}

    // Delete a book
    @DeleteMapping("/books/{bookId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBook(@Valid @PathVariable("bookId") int bookId) {return bookService.deleteBook(bookId);}

    // Update Book
    @PutMapping("/books/{bookId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateBook(@Valid @RequestBody Book book, @PathVariable("bookId") int bookId) {return bookService.updateBook(book, bookId);}

}
