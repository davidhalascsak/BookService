package com.david.book_service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    private String id;

    @Size(min=10, max=13)
    private String isbn;
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String genre;
    @NotNull
    private Double rating;

    public Book(String id, String isbn, String title, String author, String genre, Double rating) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
    }
}
