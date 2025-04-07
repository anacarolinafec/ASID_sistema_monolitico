package com.ijse.bookstore.service;

import com.ijse.bookstore.dto.NewOrderDTO;
import com.ijse.bookstore.entity.*;
import com.ijse.bookstore.repository.BookRepository;
import com.ijse.bookstore.repository.CartRepository;
import com.ijse.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import com.ijse.bookstore.repository.OrderDetailsRepository;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{
    
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;


    public OrderDetails createOrderDetails(NewOrderDTO newOrderDTO) {

        User user = userRepository.findById(newOrderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User nao existe")); //vai buscar o objeto que corresponde ao id dado

        double sum = cartService.calculateCartTotal(newOrderDTO.getUserId());

        Cart cart = cartRepository.getCartIdByUserId(newOrderDTO.getUserId());

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setUser(user);
        orderDetails.setUser(user);
        orderDetails.setSubTotal(sum);
        orderDetails.setCart(cart);

        return orderDetailsRepository.save(orderDetails);

    }
}
