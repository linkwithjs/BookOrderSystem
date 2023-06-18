package com.rj.bookordersystem.dto;

import com.rj.bookordersystem.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class OrderResponseDTO {
    private Long id;
    private Date dateAttribute;
    private Enum status;
    private String user;
    private List<Book> books;
}
