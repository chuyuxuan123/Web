package com.example.demo.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.BookDao;
import com.example.demo.model.Book;
import com.example.demo.model.BookComment;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


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

    @Override
    public JSONArray getBookComment(Integer bookId) {
        List<BookComment> comments = bookDao.findBookComment(bookId);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(comments);
        return jsonArray;
    }

    @Override
    public void addBookComment(Integer bookId, String username, String content) {
        bookDao.addBookComment(bookId, username, content);
    }

    @Override
    public List<JSONObject> validateBookInventory(JSONArray items) {

        List<JSONObject> objectList = new ArrayList<>();
        for (Object i : items
        ) {
            Long bookId = ((Integer) ((LinkedHashMap) i).get("bookId")).longValue();
            Integer amount = (Integer) ((LinkedHashMap) i).get("amount");
            Book book = bookDao.findByBookId(bookId);
            if (amount > book.getInventory()) {
                JSONObject object = new JSONObject();
                object.put("bookId", bookId);
                object.put("bookName", book.getBookname());
                object.put("inventory", book.getInventory());
                objectList.add(object);
            }
        }
        return objectList;
    }
}
