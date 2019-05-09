package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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

//    @GetMapping(path = "/add")
//    public @ResponseBody
//    String addBook(@RequestParam String ISBN,
//                   @RequestParam String bookname,
//                   @RequestParam String author,
//                   @RequestParam String cover,
//                   @RequestParam Integer inventory,
//                   @RequestParam Integer price) {
//        Book book = new Book();
//        book.setISBN(ISBN);
//        book.setBookname(bookname);
//        book.setAuthor(author);
//        book.setCover(cover);
//        book.setInventory(inventory);
//        book.setPrice(price);
//
//        bookRepository.save(book);
//        return "saved";
//    }
}
