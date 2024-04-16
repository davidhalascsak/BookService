package com.david.book_service.controller;

import com.david.book_service.exceptions.BookNotFoundException;
import com.david.book_service.model.Book;
import com.david.book_service.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/books/genres/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(bookService.getAllBooksByGenre(genre));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.saveBook(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBook.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/books/isbn/{isbn}")
    public ResponseEntity<Book> getBooksByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<Book>> getAllBooksByTitle(@RequestParam String text) {
        return ResponseEntity.ok(bookService.getAllBooksByTitle(text));
    }

    @GetMapping("/books/all")
    public ResponseEntity<List<Book>> getAllBooksByIds(@RequestParam("ids") List<String> ids) {
        return ResponseEntity.ok(bookService.getAllBooksByIds(ids));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id) throws BookNotFoundException {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
