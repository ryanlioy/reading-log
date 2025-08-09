package dev.ryanlioy.booklogger.test;

import dev.ryanlioy.booklogger.dto.EntryDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class EntryTest {

    @Test
    public void createEntry() {
        EntryDto entry = new EntryDto();
        entry.setBookId(1L);
        entry.setUserId(2L);
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
                .body("content.userId", equalTo(2))
                .body("content.bookId", equalTo(1))
                .body("content.description", equalTo("description"))
                .extract().path("content.id");

        // get
        given()
                .when()
                .get("/entry/{id}", entryId)
                .then()
                .statusCode(200)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(2))
                .body("content.bookId", equalTo(1))
                .body("content.description", equalTo("description"));

        // delete
        given()
                .when()
                .delete("/entry/{id}", entryId)
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteEntry() {
        EntryDto entry = new EntryDto();
        entry.setBookId(1L);
        entry.setUserId(2L);
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
                .body("content.userId", equalTo(2))
                .body("content.bookId", equalTo(1))
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
    public void getEntryByUserAndBOokId_doesNotExist() {
        given()
                .get("entry?userId={userId}&bookId={bookId}", 100, 100)
                .then()
                .statusCode(204);
    }

    @Test
    public void getEntryByUserAndBOokId_doesExist() {
        given()
                .get("entry?userId={userId}&bookId={bookId}", 1, 1)
                .then()
                .statusCode(200)
                .body("content", hasSize(3));
    }
}
