package dev.ryanlioy.booklogger.test;

import dev.ryanlioy.booklogger.constants.Genre;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.test.util.URLUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class Book {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = URLUtility.get();
    }

    @Test
    public void createBook() {
        BookDto book = new BookDto();
        book.setAuthor("Charles Dickens");
        book.setTitle("Some Title");
        book.setGenres(List.of(Genre.FANTASY));
        book.setPublisher("Some Publisher");
        book.setPublishDate(LocalDate.of(2020, 1, 1));

        var bookId = given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/book/add")
                .then()
                .statusCode(201)
                .body("content.id", not(nullValue()))
                .body("content.title", equalTo(book.getTitle()))
                .body("content.author", equalTo(book.getAuthor()))
                .body("content.publisher", equalTo(book.getPublisher()))
                .body("content.publishDate", equalTo(book.getPublishDate().toString()))
                .extract().body().path("content.id");

        given()
                .get("/book/" + bookId)
                .then()
                .statusCode(200)
                .body("content.id", not(nullValue()))
                .body("content.title", equalTo(book.getTitle()))
                .body("content.author", equalTo(book.getAuthor()))
                .body("content.publisher", equalTo(book.getPublisher()))
                .body("content.publishDate", equalTo(book.getPublishDate().toString()));

        // delete user
        given()
                .delete("/book/delete/" + bookId)
                .then()
                .statusCode(204);
    }
}
