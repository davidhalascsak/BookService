package com.david.book_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    BookService(BookRepository bookRepository, MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByTitle(String title) {
        String regex = ".*" + title + ".*";
        Query query = new Query(Criteria.where("title").regex(regex, "i"));
        return mongoTemplate.find(query, Book.class);
    }

    public List<Book> getAllBooksByGenre(String genre) {
        return bookRepository.findAllByGenre(genre.replaceAll("-", " "));
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
