package com.rj.bookordersystem.controller;

import com.rj.bookordersystem.dto.ResponseDTO;
import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/add-book")
    @PreAuthorize("hasAuthority('USER')")
    public Book  addBook(@RequestBody Book book) {return bookService.add(book);}

    // Fetch Single book
    @GetMapping("/books/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Book getBook(@Valid @PathVariable("id") int id) {return bookService.getBookById(id);}

    // Delete a book
    @DeleteMapping("/books/{bookId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBook(@Valid @PathVariable("bookId") int bookId) {return bookService.deleteBook(bookId);}

    // Update Book
    @PutMapping("/books/{bookId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book, @PathVariable("bookId") int bookId) {
        try {
            Book result = bookService.updateBook(book, bookId);
            if (result == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
