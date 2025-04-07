package com.ijse.bookstore.dto;

import lombok.Data;

@Data
public class BookCreationDto {

    private String title;
    private double  price;
    private int quantity;
    private String isbnNumber;
    private String description;

}
