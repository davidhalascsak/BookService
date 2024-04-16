package com.david.book_service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("books")
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
}
