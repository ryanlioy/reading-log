package dev.ryanlioy.booklogger.test;

import dev.ryanlioy.booklogger.dto.CreateCollectionDto;
import dev.ryanlioy.booklogger.dto.ModifyCollectionDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class CollectionTest {

    @Test
    public void createCollection_noBooks() {
        int userId = 1;
        String title = "title";
        String description = "description";
        boolean isDefault = false;

        CreateCollectionDto createCollection = new CreateCollectionDto();
        createCollection.setIsDefaultCollection(isDefault);
        createCollection.setTitle(title);
        createCollection.setDescription(description);
        createCollection.setUserId((long) userId);
        createCollection.setBookIds(new ArrayList<>());

        // create collection
        int collectionId = given()
                .contentType(ContentType.JSON)
                .body(createCollection)
                .when()
                .post("collection")
                .then()
                .statusCode(201)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(userId))
                .body("content.title", equalTo(title))
                .body("content.description", equalTo(description))
                .body("content.isDefaultCollection", equalTo(isDefault))
                .body("content.books", hasSize(0))
                .extract().path("content.id");

        // get collection
        given()
                .when()
                .get("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(200)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(userId))
                .body("content.title", equalTo(title))
                .body("content.description", equalTo(description))
                .body("content.isDefaultCollection", equalTo(isDefault))
                .body("content.books", hasSize(0));

        // delete collection
        given()
                .when()
                .delete("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);
    }

    @Test
    public void createCollection_withBooks() {
        int userId = 1;
        String title = "title";
        String description = "description";
        boolean isDefault = false;

        CreateCollectionDto createCollection = new CreateCollectionDto();
        createCollection.setIsDefaultCollection(isDefault);
        createCollection.setTitle(title);
        createCollection.setDescription(description);
        createCollection.setUserId((long) userId);
        createCollection.setBookIds(List.of(1L));

        // create collection
        int collectionId = given()
                .contentType(ContentType.JSON)
                .body(createCollection)
                .when()
                .post("collection")
                .then()
                .statusCode(201)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(userId))
                .body("content.title", equalTo(title))
                .body("content.description", equalTo(description))
                .body("content.isDefaultCollection", equalTo(isDefault))
                .body("content.books", hasSize(1))
                .body("content.books[0].id", equalTo(1))
                .extract().path("content.id");

        // get collection
        given()
                .when()
                .get("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(200)
                .body("content.id", not(nullValue()))
                .body("content.userId", equalTo(userId))
                .body("content.title", equalTo(title))
                .body("content.description", equalTo(description))
                .body("content.isDefaultCollection", equalTo(isDefault))
                .body("content.books", hasSize(1));

        // delete collection
        given()
                .when()
                .delete("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);
    }

    @Test
    public void createCollection_userDoesNotExist() {
        CreateCollectionDto createCollection = new CreateCollectionDto();
        createCollection.setIsDefaultCollection(false);
        createCollection.setTitle("title");
        createCollection.setDescription("description");
        createCollection.setUserId(400L);
        createCollection.setBookIds(List.of(1L));

        // create collection
        given()
                .contentType(ContentType.JSON)
                .body(createCollection)
                .when()
                .post("collection")
                .then()
                .statusCode(400)
                .body("errors", hasSize(1))
                .body("errors[0].message", equalTo("User does not exist"));
    }

    @Test
    public void createCollection_bookDoesNotExist() {
        long bookId = 400;
        CreateCollectionDto createCollection = new CreateCollectionDto();
        createCollection.setIsDefaultCollection(false);
        createCollection.setTitle("title");
        createCollection.setDescription("description");
        createCollection.setUserId(1L);
        createCollection.setBookIds(List.of(bookId));

        // create collection
        given()
                .contentType(ContentType.JSON)
                .body(createCollection)
                .when()
                .post("collection")
                .then()
                .statusCode(400)
                .body("errors", hasSize(1))
                .body("errors[0].message", equalTo(String.format("Books with IDs=[%s] do not exist", bookId)));
    }

    @Test
    public void deleteCollection_exists() {
        CreateCollectionDto createCollection = new CreateCollectionDto();
        createCollection.setIsDefaultCollection(false);
        createCollection.setTitle("title");
        createCollection.setDescription("description");
        createCollection.setUserId(1L);
        createCollection.setBookIds(List.of(1L));

        // create collection
        int collectionId = given()
                .contentType(ContentType.JSON)
                .body(createCollection)
                .when()
                .post("collection")
                .then()
                .statusCode(201)
                .extract().path("content.id");

        // delete collection
        given()
                .when()
                .delete("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);

        // get, collection should not exist
        given()
                .when()
                .get("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteCollection_doesNotExists() {
        int collectionId = 400;
        // delete collection that does not exist
        given()
                .when()
                .delete("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);

        // get collection, should not exist
        given()
                .when()
                .get("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);
    }

    @Test
    public void updateCollection_addBook() {
        CreateCollectionDto createCollection = new CreateCollectionDto();
        createCollection.setIsDefaultCollection(false);
        createCollection.setTitle("title");
        createCollection.setDescription("description");
        createCollection.setUserId(1L);
        createCollection.setBookIds(new ArrayList<>());

        // create collection
        int collectionId = given()
                .contentType(ContentType.JSON)
                .body(createCollection)
                .when()
                .post("collection")
                .then()
                .statusCode(201)
                .extract().path("content.id");

        ModifyCollectionDto modifyCollection = new  ModifyCollectionDto();
        modifyCollection.setCollectionId((long) collectionId);
        modifyCollection.setBookIds(List.of(1L));

        // add book
        given()
                .contentType(ContentType.JSON)
                .body(modifyCollection)
                .when()
                .post("/collection/add")
                .then()
                .statusCode(200)
                .body("content.id", equalTo(collectionId))
                .body("content.books",  hasSize(1));

        // delete
        given()
                .when()
                .delete("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);
    }

    @Test
    public void updateCollection_removeBook() {
        CreateCollectionDto createCollection =  new CreateCollectionDto();
        createCollection.setIsDefaultCollection(false);
        createCollection.setTitle("title");
        createCollection.setDescription("description");
        createCollection.setUserId(1L);
        createCollection.setBookIds(List.of(1L));

        // create collection with book
        int collectionId = given()
                .contentType(ContentType.JSON)
                .body(createCollection)
                .when()
                .post("collection")
                .then()
                .statusCode(201)
                .body("content.books",   hasSize(1))
                .extract().path("content.id");

        // remove book
        ModifyCollectionDto modifyCollection = new  ModifyCollectionDto();
        modifyCollection.setCollectionId((long) collectionId);
        modifyCollection.setBookIds(List.of(1L));

        given()
                .contentType(ContentType.JSON)
                .body(modifyCollection)
                .when()
                .post("/collection/remove")
                .then()
                .statusCode(200);

        // check that collection no longer has book
        given()
                .when()
                .get("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(200)
                .body("content.books", hasSize(0));

        // delete collection
        given()
                .when()
                .delete("/collection/{collectionId}", collectionId)
                .then()
                .statusCode(204);
    }
}
