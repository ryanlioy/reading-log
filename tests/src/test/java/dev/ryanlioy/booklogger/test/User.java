package dev.ryanlioy.booklogger.test;

import dev.ryanlioy.booklogger.dto.UserDto;
import dev.ryanlioy.booklogger.test.util.URLUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class User {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = URLUtility.get();
    }

    @Test
    public void testCreateUser() {
        UserDto user = new UserDto();
        user.setUsername("username");
        user.setRole("USER");

        // create user
        int userId = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/user/add")
                .then()
                .statusCode(201)
                .body("content.id", not(nullValue()))
                .body("content.username", equalTo(user.getUsername()))
                .body("content.role", equalTo(user.getRole()))
                .body("content.collections", aMapWithSize(4))
                .extract().body().path("content.id");

        // get user
        given()
                .get("/user/" + userId)
                .then()
                .statusCode(200)
                .body("content.id", equalTo(userId))
                .body("content.username", equalTo(user.getUsername()))
                .body("content.role", equalTo(user.getRole()))
                .body("content.collections", aMapWithSize(4));

        // delete user
        given()
                .delete("/user/" + userId)
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteUser() {
        UserDto user = new UserDto();
        user.setUsername("username");
        user.setRole("USER");

        // create user
        int userId = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/user/add")
                .then()
                .statusCode(201)
                .extract().body().path("content.id");

        // delete user
        given()
                .delete("/user/" + userId)
                .then()
                .statusCode(204);

        // verify user does not exist
        given()
                .get("/user/" + userId)
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetUser_doesNotExist() {
        // verify user does not exist
        given()
                .get("/user/1000")
                .then()
                .statusCode(204);
    }
}