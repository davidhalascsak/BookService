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
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/topRated")
    public ResponseEntity<List<Book>> getTopRatedBooks() {
        return ResponseEntity.ok(bookService.getTopRatedBooks());
    }

    @GetMapping("/genres/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(bookService.getAllBooksByGenre(genre));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.saveBook(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{isbn}")
                .buildAndExpand(newBook.getIsbn())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBooksByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> getAllBooksByTitle(@RequestParam String text) {
        return ResponseEntity.ok(bookService.getAllBooksByTitle(text));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooksByIds(@RequestParam("ids") List<String> ids) {
        return ResponseEntity.ok(bookService.getAllBooksByIds(ids));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
