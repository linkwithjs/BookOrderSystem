package com.rj.bookordersystem.repository;

import org.springframework.data.repository.CrudRepository;
import com.rj.bookordersystem.models.Book;
public interface BookRepository extends CrudRepository<Book, Integer> {
    public Book findById(int id);
}
