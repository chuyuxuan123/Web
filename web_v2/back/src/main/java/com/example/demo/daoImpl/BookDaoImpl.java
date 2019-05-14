package com.example.demo.daoImpl;

import com.example.demo.dao.BookDao;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book findByBookname(String bookname) {
        return bookRepository.findByBookname(bookname);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByBookId(Long bookId) {
        return bookRepository.findByBookId(bookId);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }


}
