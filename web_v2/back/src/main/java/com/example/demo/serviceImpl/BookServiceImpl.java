package com.example.demo.serviceImpl;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        this.bookRepository = repository;
    }

    @Override
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        return bookRepository.findByIsbn(ISBN);
    }

    @Override
    public Integer addABook() {
        return 200;
    }
}
