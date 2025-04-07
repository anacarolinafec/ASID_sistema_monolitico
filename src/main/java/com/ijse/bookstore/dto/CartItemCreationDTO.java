package com.ijse.bookstore.dto;

import com.ijse.bookstore.entity.Book;
import lombok.Data;

@Data

public class CartItemCreationDTO {

private long bookId;
private int quantity;
private long userId;
private long cartId;
/* private String cartId; cada user so tem um cart por isso nao e necessario passar o cartid*/

}
