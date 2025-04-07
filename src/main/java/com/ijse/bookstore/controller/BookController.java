package com.ijse.bookstore.controller;

import java.util.List;
import java.util.Map;

import com.ijse.bookstore.dto.BookCreationDto;
import com.ijse.bookstore.dto.UserRegistrationDto;
import com.ijse.bookstore.entity.User;
import com.ijse.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ijse.bookstore.entity.Book;
import com.ijse.bookstore.service.BookService;



@RestController
public class BookController {
    
    @Autowired
    private BookService bookSerivce;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookSerivce.getAllBook();
        
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){

        Book existBook = bookSerivce.getBookById(id);

        if(existBook !=null){
            return new ResponseEntity<>(existBook,HttpStatus.OK);

        } else{

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books/category/{id}")
    public ResponseEntity<List<Book>> getBooksByCategoryID(@PathVariable Long id) {

        List<Book> existBook = bookSerivce.getBooksByCategoryID(id);

         if(existBook !=null){

            return new ResponseEntity<>(existBook,HttpStatus.OK);

        } else{

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/updatequantity/{id}")
    public ResponseEntity<Book> patchQuantity(@PathVariable Long id , @RequestBody Book book){

        Book updatedBookQuantity = bookSerivce.patchBookQuantity(id,book);

        return new ResponseEntity<>(updatedBookQuantity,HttpStatus.OK);
    }

    @PostMapping("/book/register")
    public ResponseEntity<?> registerBook(@RequestBody BookCreationDto book){

        if(bookRepository.findByTitle(book.getTitle()) != null){
            return ResponseEntity.badRequest().body("Book already exists");
        }

        Book newbook = new Book();
        newbook.setTitle(book.getTitle());
        newbook.setPrice(book.getPrice());
        newbook.setQuantity(book.getQuantity());
        newbook.setIsbnNumber(book.getIsbnNumber());
        newbook.setDescription(book.getDescription());

        bookRepository.save(newbook);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }


}
