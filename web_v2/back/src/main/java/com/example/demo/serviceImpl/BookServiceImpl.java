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
    public Book getBookById(Long bookId) {
        return bookDao.findByBookId(bookId);
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        return bookDao.findByIsbn(ISBN);
    }

    @Override
    public Integer addABook(Book book) {

        bookDao.save(book);
        return 200;
    }

    @Override
    public void deleteBook(Long bookId) {
        bookDao.deleteBook(bookId);
    }

    @Override
    public void modifyBook(Book book) {
        bookDao.save(book);
    }
}
