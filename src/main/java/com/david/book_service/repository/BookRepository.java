package com.david.book_service.repository;

import com.david.book_service.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAllByGenre(String genre);
    List<Book> findAllByIdIn(List<String> ids);
}
