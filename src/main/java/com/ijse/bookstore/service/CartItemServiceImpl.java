package com.ijse.bookstore.service;

import java.util.List;
import java.util.Optional;

import com.ijse.bookstore.dto.CartItemCreationDTO;
import com.ijse.bookstore.entity.Book;
import com.ijse.bookstore.entity.Cart;
import com.ijse.bookstore.repository.BookRepository;
import com.ijse.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.bookstore.entity.CartItem;
import com.ijse.bookstore.entity.User;
import com.ijse.bookstore.repository.CartItemRepository;
import com.ijse.bookstore.repository.UserRepository;

@Service
public class CartItemServiceImpl implements CartItemService{

    // Injeção de dependências dos repositórios necessários para aceder à base de dados
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CartRepository cartRepository;

    // Metodo que cria um novo CartItem a partir de um DTO (objeto de transferência de dados)
    @Override
    public CartItem createCartItem(CartItemCreationDTO createCartItem){
    //Este metodo recebe o DTO com os dados do livro, utilizador, carrinho e quantidade

        User id_user = userRepository.findById(createCartItem.getUserId()).orElseThrow();
        Book book = bookRepository.findById(createCartItem.getBookId()).orElseThrow();
        double sum = createCartItem.getQuantity() * book.getPrice();
        Cart id_cart  = cartRepository.findById(createCartItem.getCartId()).orElseThrow();

        CartItem cartItem = new CartItem();
        cartItem.setBookid(book);
        cartItem.setUser(id_user);
        cartItem.setQuantity(createCartItem.getQuantity());
        cartItem.setUnitPrice(book.getPrice());
        cartItem.setSubTotal(sum);
        cartItem.setCart(id_cart);

        System.out.println("Quantity: " + createCartItem.getQuantity());
        System.out.println("Unit Price: " + book.getPrice());
        System.out.println("Subtotal: " + sum);

        return cartItemRepository.save(cartItem);

    }

   public List<CartItem> getAllCartitem(){

        return cartItemRepository.findAll();


    }

    public CartItem getCartItemById(Long id){

        return cartItemRepository.findById(id).orElse(null);
    }


    public CartItem patchCartQuantity(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if (existItem != null) {
            Book book = existItem.getBookid(); // obtém o livro associado
            int requestedQuantity = cartItem.getQuantity();
            int availableStock = book.getQuantity();

            if (requestedQuantity > availableStock) {
                throw new IllegalArgumentException("Quantidade excede o stock disponível (" + availableStock + ").");
            }

            existItem.setQuantity(requestedQuantity);
            existItem.setSubTotal(book.getPrice() * requestedQuantity); // atualizar subtotal também
            cartItemRepository.save(existItem);

            return existItem;
        } else {
            return null;
        }
    }


    public CartItem patchCartSubTotal(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);
    
        if (existItem != null) {
            
            existItem.setSubTotal(cartItem.getSubTotal());
            cartItemRepository.save(existItem);
    
            return existItem;
        } else { 
            return null;
        }
    }


    public CartItem deleteCartItyItemById(Long id){

        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if(existItem !=null){

            cartItemRepository.delete(existItem);
        }
        return null;
    }


    public void clearCart(){

        cartItemRepository.deleteAll();
        
    }

    public void resetAutoIncrement() {
        cartItemRepository.resetAutoIncrement();
    }

    public List<CartItem> getCartItemsByUsername(String username) {

        return cartItemRepository.findByUser_Username(username);
        
    }
}
