package my_library.demo.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import my_library.demo.model.Book;
import my_library.demo.services.book_service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(MockitoJUnitRunner.class)
public class ControllerBookTest {

    @Mock
    private BookService bookService;


    @BeforeAll
    public static void setUp(){
        RestAssured.port=DEFAULT_PORT;
    }
    @Test
    public void addBookRequest(){
        MockitoAnnotations.initMocks(this);
        lenient().when(bookService.addBook("tytul", "autor", "gatunek")).thenReturn(new Book());
        Response response = given()
                .param("title", "tytul")
                .param("author", "autor")
                .param("genre", "gatunek")
                .when()
                .post("/controller_book/addBook")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Book book = response.body().as(Book.class);

        assertNotNull(book);
        assertEquals(book.getTitle(), "tytul");
        assertEquals(book.getAuthor(), "autor");
        assertEquals(book.getGenre(),"gatunek");
    }

    //@Test
    public void addBookExceptionRequest(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(bookService.addBook("tytul1", "autor1", "gatunek1")).thenThrow(new RuntimeException("test"));
        Response response = given()
                .param("title", "tytul1")
                .param("author", "autor1")
                .param("genre", "gatunek1")
                .when()
                .post("/controller_book/addBook")
                .then()
                .statusCode(200)
                .extract()
                .response();

        RuntimeException runtimeException = response.body().as(RuntimeException.class);

        assertEquals(runtimeException.getMessage(), "test");

    }

    @Test
    public void searchBookRequest(){
        given()
                .param("id", 0)
                .when()
                .get("/controller_book/searchBook")
                .then()
                .statusCode(500);
    }

    @Test
    public void searchBookByTitleRequest(){
        given()
                .param("title", "tytul")
                .when()
                .get("/controller_book/searchBookByTitle")
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteBookRequest(){
        given()
                .param("id",0)
                .when()
                .delete("/controller_book/deleteBook")
                .then()
                .statusCode(500);
    }
}
