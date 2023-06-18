package com.rj.bookordersystem.dto;

import com.rj.bookordersystem.models.Book;
import com.rj.bookordersystem.models.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
    @Getter
    @Setter
    public class OrderDTO {
        private Date dateAttribute;
        private Order.StatusEnum status;
        private List<Book> books;
    }

