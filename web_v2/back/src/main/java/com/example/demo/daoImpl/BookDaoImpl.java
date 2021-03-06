package com.example.demo.daoImpl;

import com.example.demo.dao.BookDao;
import com.example.demo.model.Book;
import com.example.demo.model.BookComment;
import com.example.demo.repository.BookCommentRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCommentRepository bookCommentRepository;

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
        List<Book> rawList = bookRepository.findAll();
        List<Book> reList = new ArrayList<>();
        for (Book b : rawList
        ) {
            if (!b.isDeleted()) {
                reList.add(b);
            }
        }
        return reList;
    }

    @Override
    public Book findByBookId(Long bookId) {
        return bookRepository.findByBookId(bookId);
    }

    @Override
    public void save(Book book) {
        bookRepository.saveAndFlush(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteBook(true, bookId);
    }

    @Override
    public List<BookComment> findBookComment(Integer bookId) {
        return bookCommentRepository.findByBookId(bookId);
    }

    @Override
    public void addBookComment(Integer bookId, String username, String content) {
        BookComment bookComment = new BookComment();
        bookComment.setBookId(bookId);
        bookComment.setContent(content);
        bookComment.setUsername(username);
        bookComment.setCreateDate(new Date());

        bookCommentRepository.save(bookComment);
    }
}
