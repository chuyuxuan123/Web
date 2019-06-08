package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);

    Book findByBookname(String bookname);

    Book findByBookId(Long bookId);

    @Transactional
    @Modifying
    @Query(value = "update book set deleted = :deleted where book_id = :book_id", nativeQuery = true)
    void deleteBook(@Param("deleted") boolean deleted, @Param("book_id") Long bookId);

    @Transactional
    @Modifying
    @Query(value = "update book set bookname = :bookname, " +
            "author = :author, " +
            "inventory = :inventory, " +
            "price = :price, " +
            "isbn = :isbn" +
            "where book_id = :book_id", nativeQuery = true)
    void modifyBookInfo(@Param("bookname") String bookname,
                        @Param("author") String author,
                        @Param("inventory") Integer inventory,
                        @Param("price") Integer price,
                        @Param("isbn") String isbn,
                        @Param("book_id") Long bookId);
}