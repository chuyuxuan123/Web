package com.example.demo.repository;

import com.example.demo.model.BookComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {

    List<BookComment> findByBookId(Integer bookId);
}
