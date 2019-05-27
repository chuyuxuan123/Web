package com.example.demo.service;

import com.example.demo.model.Book;
import org.springframework.web.bind.annotation.RequestParam;


public interface BookService {

    Iterable<Book> getAllBooks();

    Book getBookById(Long bookId);

    Book getBookByISBN(String ISBN);

    Integer addABook(Book book);

    void deleteBook(Long bookId);

    void modifyBook(Book book);
}
