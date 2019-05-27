package com.example.demo.dao;

import com.example.demo.model.Book;

import java.util.List;

public interface BookDao {
    Book findByIsbn(String isbn);

    Book findByBookname(String bookname);

    List<Book> findAll();

    Book findByBookId(Long bookId);

    void deleteBook(Long bookId);

    void save(Book book);
}
