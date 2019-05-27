package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping(path = "/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService service) {
        this.bookService = service;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    Book getBookByISBN(@RequestParam String ISBN) {
        return bookService.getBookByISBN(ISBN);
    }


    @PostMapping(path = "/add")
    public @ResponseBody
    String addBook(@RequestBody JSONObject data) {

        System.out.println(data);
        Book book = new Book();
        book.setIsbn(data.getString("isbn"));
        book.setBookname(data.getString("bookname"));
        book.setAuthor(data.getString("author"));
        book.setCover("/" + data.getString("cover"));
        book.setInventory(data.getInteger("inventory"));
        book.setPrice(data.getInteger("price"));
        System.out.println(book);

        bookService.addABook(book);


        return "saved";
    }

    @DeleteMapping
    public @ResponseBody
    String deleteBook(@RequestParam(value = "bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return "deleted";
    }

    @PutMapping
    public @ResponseBody
    String modifyBookInfo(@RequestBody JSONObject data) {
        Book book = bookService.getBookById(data.getLong("bookId"));
        book.setIsbn(data.getString("ISBN"));
        book.setBookname(data.getString("bookname"));
        book.setAuthor(data.getString("author"));
        book.setInventory(data.getInteger("inventory"));
        book.setPrice(data.getInteger("price"));
        bookService.modifyBook(book);
        return "saved";
    }
}
