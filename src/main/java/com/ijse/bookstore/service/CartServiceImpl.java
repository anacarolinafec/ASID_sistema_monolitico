package com.ijse.bookstore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ijse.bookstore.dto.CartCreationDto;
import com.ijse.bookstore.entity.CartItem;
import com.ijse.bookstore.entity.User;
import com.ijse.bookstore.repository.CartItemRepository;
import com.ijse.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.bookstore.entity.Cart;
import com.ijse.bookstore.repository.CartRepository;


@Service
public class CartServiceImpl implements CartService{
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public Cart createCart(CartCreationDto cartCreationDto) {

        User user_id = userRepository.findById(cartCreationDto.getId_user()).orElseThrow();

        Cart cart = new Cart();
        cart.setUser(user_id);
        cart.setCreatedDate(LocalDate.now()); // backend define isto

        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCart(){

        return cartRepository.findAll();
    }

    @Override
    public Cart getCartIdByUserId(Long userId){

        return cartRepository.getCartIdByUserId(userId);
    }

    public double calculateCartTotal(long userId) {
        Cart cart = cartRepository.getCartIdByUserId(userId);
        Long cartId = cart.getId();

        List<CartItem> items = cartItemRepository.findByCartId(cartId);

        return items.stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
    }

}
