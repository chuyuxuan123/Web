package com.example.demo.service;

import com.example.demo.model.Book;
import org.springframework.web.bind.annotation.RequestParam;


public interface BookService {

    Iterable<Book> getAllBooks();

    Book getBookByISBN(@RequestParam String ISBN);

    Integer addABook();

}
