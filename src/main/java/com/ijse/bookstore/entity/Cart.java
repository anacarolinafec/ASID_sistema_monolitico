package com.ijse.bookstore.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartid")
    private Long id;

    @Column
    private LocalDate createdDate;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @JoinColumn(name="cartItem_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
}
