package com.ijse.bookstore.service;

import com.ijse.bookstore.dto.NewOrderDTO;
import org.springframework.stereotype.Service;

import com.ijse.bookstore.entity.OrderDetails;

@Service
public interface OrderDetailsService {
    OrderDetails createOrderDetails(NewOrderDTO newOrderDTO);
}
