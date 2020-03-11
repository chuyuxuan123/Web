package com.example.demo.service;

import com.example.demo.dto.BookInfoDTO;

public interface RemoteBookService {
    BookInfoDTO getBookInfoByName(String bookname);
}
