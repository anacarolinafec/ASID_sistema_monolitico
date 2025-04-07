package com.ijse.bookstore.controller;

import com.ijse.bookstore.dto.NewOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.bookstore.entity.OrderDetails;
import com.ijse.bookstore.service.OrderDetailsService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderDetailsController {
    
    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping("/orderdetails")
    public ResponseEntity<OrderDetails> createOrderDetails(@RequestBody NewOrderDTO newOrderDTO) {

        OrderDetails orderedDetails = orderDetailsService.createOrderDetails(newOrderDTO);

        return new ResponseEntity<>(orderedDetails,HttpStatus.CREATED);

    }
}
