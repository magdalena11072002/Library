package my_library.demo.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;

@SpringBootTest
public class ControllerBookTest {

    @Test
    public void addBookRequest(){ //odrzuca połaczenie
        given()
                .contentType(ContentType.JSON)
                .param("title", "tytul")
                .param("author", "autor")
                .param("genre", "gatunek")
                .when()
                .post("/controller-book/addBook")
                .then()
                .statusCode(200);
    }
}
