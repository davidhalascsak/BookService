package com.david.book_service.service;

import com.david.book_service.exceptions.BookNotFoundException;
import com.david.book_service.model.Book;
import com.david.book_service.repository.BookRepository;
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
    public BookService(BookRepository bookRepository, MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findByIsbn(isbn);

        if(book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("The book is not found!");
        }
    }

    public Book findBookById(String id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);

        if(book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("The book is not found!");
        }
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

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(String id) throws BookNotFoundException {
        findBookById(id);
        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooksByIds(List<String> ids) {
        return bookRepository.findAllByIdIn(ids);
    }
}
