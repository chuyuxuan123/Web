package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Book;

import java.util.List;


public interface BookService {

    Iterable<Book> getAllBooks();

    Book getBookById(Long bookId);

    Book getBookByISBN(String ISBN);

    Integer addABook(Book book);

    void deleteBook(Long bookId);

    void modifyBook(Book book);

    JSONArray getBookComment(Integer bookId);

    void addBookComment(Integer bookId, String username, String content);

    List<JSONObject> validateBookInventory(JSONArray items);
}
