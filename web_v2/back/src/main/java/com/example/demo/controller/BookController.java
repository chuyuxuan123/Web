package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping(path = "/books")
public class BookController {
    @Autowired

    private BookRepository bookrepo;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return bookrepo.findAll();
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    Book getBookByISBN(@RequestParam String ISBN) {
        if (bookrepo.findById(ISBN).isPresent())
            return bookrepo.findById(ISBN).get();
        else
            return null;
    }

    @GetMapping(path = "/add")
    public @ResponseBody
    String addBook(@RequestParam String ISBN,
                   @RequestParam String bookname,
                   @RequestParam String author,
                   @RequestParam String cover,
                   @RequestParam Integer inventory,
                   @RequestParam Integer price) {
        Book book = new Book();
        book.setISBN(ISBN);
        book.setBookname(bookname);
        book.setAuthor(author);
        book.setCover(cover);
        book.setInventory(inventory);
        book.setPrice(price);

        bookrepo.save(book);
        return "saved";
    }
}
