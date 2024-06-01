package my_library.demo.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.DEFAULT_PORT;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ControllerBorrowedBookTest {


    @BeforeAll
    public static void setUp(){
        RestAssured.port=DEFAULT_PORT;
    }

//    @Test
//    void borrowedBookRequest(){
//        given()
//                .param("title", "tytul")
//                .param("myUser", "uzytkownik")
//                .when()
//                .post("/controller_borrowed_book/borrowBook")
//                .then()
//                .statusCode(200);
//    }

    @Test
    void borrowedBookByUserRequest(){
        given()
                .param("myUser", "uzytkownik")
                .when()
                .get("/controller_borrowed_book/borrowByUser")
                .then()
                .statusCode(200);
    }

    @Test
    void returnBookRequest(){
        given()
                .param("title", "tytul")
                .param("myUser", "uzytkownik")
                .when()
                .put("/controller_borrowed_book/returnBook")
                .then()
                .statusCode(500);
    }
}
