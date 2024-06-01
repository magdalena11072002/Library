package my_library.demo.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.DEFAULT_PORT;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ControllerUserTest {

    @BeforeAll
    public static void setUp(){
        RestAssured.port=DEFAULT_PORT;
    }

    @Test
    void searchUserRequest(){
        given()
                .param("myUser", "uzytkownik")
                .when()
                .get("controller_user/searchUser")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteUserRequest(){
        given()
                .param("myUser", "uzytkownik")
                .when()
                .delete("/controller_user/deleteUser")
                .then()
                .statusCode(500);
    }

    @Test
    void addUserRequest(){
        given()
                .param("myUser", "uzytkownik")
                .when()
                .post("/controller_user/addUser")
                .then()
                .statusCode(200);
    }

}
