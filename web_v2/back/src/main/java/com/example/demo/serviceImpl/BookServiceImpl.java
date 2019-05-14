package com.example.demo.serviceImpl;

import com.example.demo.dao.BookDao;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Iterable<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        return bookDao.findByIsbn(ISBN);
    }

    @Override
    public Integer addABook() {
        return 200;
    }
}
