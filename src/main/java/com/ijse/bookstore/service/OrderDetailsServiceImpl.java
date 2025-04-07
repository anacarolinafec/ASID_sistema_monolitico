package com.ijse.bookstore.service;

import com.ijse.bookstore.dto.NewOrderDTO;
import com.ijse.bookstore.dto.OrderShippingConfirmation;
import com.ijse.bookstore.entity.*;
import com.ijse.bookstore.producer.MessageProducer;
import com.ijse.bookstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{
    
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrderDetails(NewOrderDTO newOrderDTO) {
        User user = userRepository.findById(newOrderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User nao existe")); //vai buscar o objeto que corresponde ao id dado

        Cart cart = cartRepository.getCartIdByUserId(newOrderDTO.getUserId());

        List<OrderDetails> orderDetails = createOrderDetailsFromCartItems(cart.getCartItems(), user);

        Order order = new Order();
        order.setOrderDate(Date.from(Instant.now()));
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(calculateTotalPrice(orderDetails));
        order.setUser(user);

        order = orderRepository.save(order);

        var orderShippingConfirmation = new OrderShippingConfirmation();
        orderShippingConfirmation.setOrderId(order.getId());
        orderShippingConfirmation.setUserId(user.getId());
        orderShippingConfirmation.setOrderTotal(order.getTotalPrice());
        orderShippingConfirmation.setAddress(newOrderDTO.getAddress());
        orderShippingConfirmation.setCity(newOrderDTO.getCity());
        orderShippingConfirmation.setPostalCode(newOrderDTO.getPostalCode());

        messageProducer.sendOrderShippingConfirmation(orderShippingConfirmation);

        return order;
    }

    private List<OrderDetails> createOrderDetailsFromCartItems(List<CartItem> cartItems, User user) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        for(var cartItem: cartItems){
            var book = cartItem.getBookid();

            var orderDetail = new OrderDetails();
            orderDetail.setBook(book);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setSubTotal(book.getPrice() * cartItem.getQuantity());
            orderDetail.setUser(user);

            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    private double calculateTotalPrice (List<OrderDetails> orderDetails){
        double sum = 0;
        for (var orderDetail: orderDetails){
            sum += orderDetail.getSubTotal();
        }
        return sum/orderDetails.size();
    }
}
