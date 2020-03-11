package com.example.demo.serviceImpl;

import com.example.demo.dto.BookInfoDTO;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.RemoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoteBookServiceImpl implements RemoteBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookInfoDTO getBookInfoByName(String bookname) {
        Book book = bookRepository.findByBookname(bookname);
        if (book == null) {
            return null;
        }
        BookInfoDTO dto = new BookInfoDTO();
        dto.setBookId(book.getBookId());
        dto.setBookname(book.getBookname());
        dto.setAuthor(book.getAuthor());
        dto.setInventory(book.getInventory());
        dto.setIsbn(book.getIsbn());
        return dto;
    }
}
