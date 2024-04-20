package com.david.book_service.service;

import com.david.book_service.exceptions.BookNotFoundException;
import com.david.book_service.model.Book;
import com.david.book_service.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public BookService(BookRepository bookRepository, MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Cacheable(value = "books")
    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findByIsbn(isbn);

        if(book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("The book is not found!");
        }
    }

    @CachePut(value = "books")
    public Book addToCache(Book book) {
        return book;
    }

    public List<Book> getTopRatedBooks() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "rating"));
        query.with(PageRequest.of(0, 30));

        return mongoTemplate.find(query, Book.class);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByIds(List<String> ids) {
        return bookRepository.findAllByIdIn(ids);
    }

    public List<Book> getAllBooksByTitle(String title) {
        String regex = ".*" + title + ".*";
        Query query = new Query(Criteria.where("title").regex(regex, "i"));
        return mongoTemplate.find(query, Book.class);
    }

    public List<Book> getAllBooksByGenre(String genre) {
        return bookRepository.findAllByGenre(genre.replaceAll("-", " "));
    }

    @CachePut(value = "books")
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(value = "books")
    public void deleteBook(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            bookRepository.deleteById(id);
        }
    }
}
