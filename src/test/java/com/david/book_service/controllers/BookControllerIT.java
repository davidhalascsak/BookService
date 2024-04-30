package com.david.book_service.controllers;

import com.david.book_service.model.Book;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookControllerIT {

    @Test
    public void testGetTopRatedBooks() {
        given()
                .when()
                .get("http://localhost:8100/books/topRated")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetBooksByGenreExist() {
        List<Book> books = given()
                .when()
                .pathParam("genre", "historical fiction")
                .get("http://localhost:8100/books/genres/{genre}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getList(".", Book.class);

        assertThat(books.isEmpty(), is(false));
    }

    @Test
    public void testGetBooksByGenreNotExist() {
        List<Book> books = given()
                .when()
                .pathParam("genre", "historical cooking")
                .get("http://localhost:8100/books/genres/{genre}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getList(".", Book.class);

        assertThat(books.isEmpty(), is(true));
    }
}
