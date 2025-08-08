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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class BookTest {

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

    @Test
    public void deleteBook() {
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

        // delete user
        given()
                .delete("/book/delete/" + bookId)
                .then()
                .statusCode(204);

        // get book, should not exist
        given()
                .get("/book/" + bookId)
                .then()
                .statusCode(204);
    }

    @Test
    public void getBook_doesNotExist() {
        given()
                .get("/book/400")
                .then()
                .statusCode(204);
    }

    @Test
    public void getBook_bookExists() {
        given()
                .get("/book/1")
                .then()
                .statusCode(200)
                .body("content.id", equalTo(1))
                .body("content.title", equalTo("The Fellowship of the Ring"))
                .body("content.author", equalTo("J. R. R. Tolkien"))
                .body("content.publisher", equalTo("Allen & Unwin"))
                .body("content.publishDate", equalTo("1954-07-29"))
                .body("content.genres", contains("FANTASY", "FICTION"));
    }
}
