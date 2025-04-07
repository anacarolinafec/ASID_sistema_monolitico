package com.ijse.bookstore.dto;

import lombok.Data;

@Data
public class OrderShippingConfirmation {
    private Long orderId;
    private Long userId;

    private double orderTotal;
}
