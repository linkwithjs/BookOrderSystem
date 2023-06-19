package com.rj.bookordersystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.rj.bookordersystem.models.Book;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    public Book findById(int id);

    @Query(value = "SELECT * FROM books where quantity > 0", nativeQuery = true)
    List<Book> findBooksByQuantityGreaterThanZero();

}
