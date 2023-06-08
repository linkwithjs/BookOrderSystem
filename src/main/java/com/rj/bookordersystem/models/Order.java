package com.rj.bookordersystem.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Order(Date dateAttribute, StatusEnum status, User user) {
        this.dateAttribute = dateAttribute;
        this.status = status;
        this.user = user;
    }

    @Column(nullable = true)
    private Date dateAttribute;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    private List<Book> books;
    public enum StatusEnum {
        ACCEPT,
        DECLINE
    }
}
