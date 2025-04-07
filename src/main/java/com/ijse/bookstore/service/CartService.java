package com.ijse.bookstore.service;

import java.util.List;

import com.ijse.bookstore.dto.CartCreationDto;
import org.springframework.stereotype.Service;

import com.ijse.bookstore.entity.Cart;

@Service
public interface CartService {

    Cart createCart(CartCreationDto cartCreationDto);
    List<Cart> getAllCart();
    Cart getCartIdByUserId(Long userId);
    double calculateCartTotal(long userId);
}
