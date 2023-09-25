package com.david.book_service.controller;

import com.david.book_service.model.Book;
import com.david.book_service.exceptions.BookNotFoundException;
import com.david.book_service.repository.BookRepository;
import com.david.book_service.service.BookService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    BookService bookService;

    public BookController(BookRepository bookRepository, MongoTemplate mongoTemplate) {
        this.bookService = new BookService(bookRepository, mongoTemplate);
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello K8S";
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/genres/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getAllBooksByGenre(genre);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.saveBook(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getIsbn())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/books/isbn/{isbn}")
    public Book getBooksByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        Optional<Book> book = bookService.findBookByIsbn(isbn);

        if(book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("The book is not found!");
        }
    }

    @GetMapping("/books/search")
    public List<Book> getAllBooksByTitle(@RequestParam String text) {
        return bookService.getAllBooksByTitle(text);
    }

    @DeleteMapping("/books/{isbn}")
    public void deleteBooksByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        Optional<Book> book = bookService.findBookByIsbn(isbn);

        if(book.isPresent()) {
            bookService.deleteBook(book.get().getId());
        } else {
            throw new BookNotFoundException("The book is not found!");
        }
    }
}
