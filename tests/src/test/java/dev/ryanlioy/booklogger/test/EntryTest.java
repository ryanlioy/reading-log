package dev.ryanlioy.booklogger.test;

import dev.ryanlioy.booklogger.dto.EntryDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class EntryTest {

    @Test
    public void createEntry() {
        int userId = 2;
        int bookId = 1;
        EntryDto entry = new EntryDto();
        entry.setBookId((long) bookId);
        entry.setUserId((long) userId);
        LocalDateTime now = LocalDateTime.now();
        entry.setCreationDate(now);
        entry.setDescription("description");

        // create
        int entryId = given()
                .contentType(ContentType.JSON)
                .body(entry)
                .when()
                .post("/entry")
                .then()
                .statusCode(201)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(userId))
                .body("content.bookId", equalTo(bookId))
                .body("content.description", equalTo("description"))
                .extract().path("content.id");

        // get by entry id
        given()
                .when()
                .get("/entry/{id}", entryId)
                .then()
                .statusCode(200)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(userId))
                .body("content.bookId", equalTo(bookId))
                .body("content.description", equalTo("description"));

        // get by user and book id
        given()
                .get("entry?userId={userId}&bookId={bookId}", userId, bookId)
                .then()
                .statusCode(200)
                .body("content.id", hasItem(entryId));

        // delete
        given()
                .when()
                .delete("/entry/{id}", entryId)
                .then()
                .statusCode(204);
    }

    @Test
    public void createEntry_userDoesNotExist() {
        EntryDto entry = new EntryDto();
        entry.setBookId(1L);
        entry.setUserId(100L);
        LocalDateTime now = LocalDateTime.now();
        entry.setCreationDate(now);
        entry.setDescription("description");

        // attempt to create
        given()
                .contentType(ContentType.JSON)
                .body(entry)
                .when()
                .post("/entry")
                .then()
                .statusCode(400)
                .body("content", nullValue())
                .body("errors", hasSize(1))
                .body("errors[0].message", equalTo("User does not exist"));
    }

    @Test
    public void createEntry_bookDoesNotExist() {
        EntryDto entry = new EntryDto();
        entry.setBookId(100L);
        entry.setUserId(1L);
        LocalDateTime now = LocalDateTime.now();
        entry.setCreationDate(now);
        entry.setDescription("description");

        // attempt to create
        given()
                .contentType(ContentType.JSON)
                .body(entry)
                .when()
                .post("/entry")
                .then()
                .statusCode(400)
                .body("content", nullValue())
                .body("errors", hasSize(1))
                .body("errors[0].message", equalTo("Book does not exist"));
    }

    @Test
    public void deleteEntry() {
        int userId = 2;
        int bookId = 1;
        EntryDto entry = new EntryDto();
        entry.setBookId((long) bookId);
        entry.setUserId((long) userId);
        LocalDateTime now = LocalDateTime.now();
        entry.setCreationDate(now);
        entry.setDescription("description");

        // create
        int entryId = given()
                .contentType(ContentType.JSON)
                .body(entry)
                .when()
                .post("/entry")
                .then()
                .statusCode(201)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(userId))
                .body("content.bookId", equalTo(bookId))
                .body("content.description", equalTo("description"))
                .extract().path("content.id");

        // delete
        given()
                .when()
                .delete("/entry/{id}", entryId)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/entry/{id}", entryId)
                .then()
                .statusCode(204);
    }

    @Test
    public void getEntry_doesNotExist() {
        given()
                .get("/entry/{id}", 400)
                .then()
                .statusCode(204);
    }

    @Test
    public void getEntry_entryExists() {
        given()
                .get("entry/{id}", 1)
                .then()
                .statusCode(200)
                .body("content.id", equalTo(1))
                .body("content.userId", equalTo(1))
                .body("content.bookId", equalTo(1))
                .body("content.description", equalTo("Read first 3 chapters"))
                .body("content.creationDate", equalTo("2025-07-01T00:00:00"));
    }

    @Test
    public void getEntryByUserAndBookId_doesNotExist() {
        given()
                .get("entry?userId={userId}&bookId={bookId}", 100, 100)
                .then()
                .statusCode(204);
    }

    @Test
    public void getEntryByUserAndBookId_doesExist() {
        given()
                .get("entry?userId={userId}&bookId={bookId}", 1, 1)
                .then()
                .statusCode(200)
                .body("content", hasSize(3));
    }

    @Test
    public void getEntryByUserAndBookId_onlyReturnEntriesForSpecifiedBookAndUser() {
        given()
                .get("entry?userId={userId}&bookId={bookId}", 1, 1)
                .then()
                .statusCode(200)
                .body("content.id", not(hasItem(4))); // entry for another book
    }
}
