package com.rj.bookordersystem.controller;

import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> list = bookService.getAllBooks();
        if (list.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else
            return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @PostMapping("/add-book")
    @PreAuthorize("hasAuthority('USER')")
    public Book  addBook(@RequestBody Book book) {
//        Book b = null;
//        try {
//            b = this.bookService.addBook(book);
//            return ResponseEntity.of(Optional.of(b));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
        return bookService.add(book);

    }

    // Fetch Single book
    @GetMapping("/books/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(book));
    }


    // Delete a book
    @DeleteMapping("/books/{bookId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int bookId) {
        try {
            // System.out.println("Book id : " + bookId);
            this.bookService.deleteBook(bookId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update Book
    @PutMapping("/books/{bookId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("bookId") int bookId) {
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
