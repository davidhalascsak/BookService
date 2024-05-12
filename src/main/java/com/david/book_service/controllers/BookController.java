package com.david.book_service.controllers;

import com.david.book_service.exceptions.BookNotFoundException;
import com.david.book_service.models.data.BookDTO;
import com.david.book_service.services.BookService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<BookDTO>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/topRated")
    public ResponseEntity<List<BookDTO>> getTopRatedBooks() {
        return ResponseEntity.ok(bookService.getTopRatedBooks());
    }

    @GetMapping("/genres/{genre}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(bookService.getAllBooksByGenre(genre));
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {
        BookDTO newBookDTO = bookService.saveBook(bookDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{isbn}")
                .buildAndExpand(newBookDTO.getIsbn())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBooksByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> getAllBooksByTitle(@RequestParam String text) {
        return ResponseEntity.ok(bookService.getAllBooksByTitle(text));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooksByIds(@RequestParam("ids") List<String> ids) {
        return ResponseEntity.ok(bookService.getAllBooksByIds(ids));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
