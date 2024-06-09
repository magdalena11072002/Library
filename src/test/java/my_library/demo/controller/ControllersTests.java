package my_library.demo.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import my_library.demo.model.Book;
import my_library.demo.model.BorrowedBook;
import my_library.demo.model.MyUser;
import my_library.demo.services.book_service.BookService;
import my_library.demo.services.borrowed_book.BorrowedBookService;
import my_library.demo.services.my_user_service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.DEFAULT_PORT;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ControllersTests {

    @MockBean
    BookService bookService;

    @MockBean
    BorrowedBookService borrowedBookService;

    @MockBean
    UserService userService;

    @BeforeAll
    public static void setUp(){
        RestAssured.port=DEFAULT_PORT;
    }
    @Test
    public void addBookRequest(){
        Book myBook=new Book();
        Mockito.when(bookService.addBook("tytul", "autor", "gatunek")).thenReturn(myBook);
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
        assertEquals(book.getTitle(), myBook.getTitle());
        assertEquals(book.getAuthor(), myBook.getAuthor());
        assertEquals(book.getGenre(), myBook.getGenre());
    }

    //@Test
    public void addBookExceptionRequest(){
        Mockito.when(bookService.addBook("tytul1", "autor1", "gatunek1")).thenThrow(new RuntimeException("test"));
        Response response = given()
                .contentType(ContentType.JSON)
                .param("title", "tytul1")
                .param("author", "autor1")
                .param("genre", "gatunek1")
                .when()
                .post("/controller_book/addBook")
                .then()
                .statusCode(400)
                .extract()
                .response();

        RuntimeException runtimeException = response.body().as(RuntimeException.class);

        assertEquals(runtimeException.getMessage(), "test");

    }

    @Test
    public void searchBookRequest(){
        Book myBook=new Book();
        Mockito.when(bookService.searchBook(0L)).thenReturn(Optional.of(myBook));
        Response response = given()
                .param("id", 0)
                .when()
                .get("/controller_book/searchBook")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Book book = response.body().as(Book.class);

        assertNotNull(book);
        assertEquals(book.getId(), myBook.getId());

    }

    @Test
    public void searchBookByTitleRequest(){
        Book myBook=new Book();
        Mockito.when(bookService.searchBookByTitle("tytul")).thenReturn(myBook);
        Response response = given()
                .param("title", "tytul")
                .when()
                .get("/controller_book/searchBookByTitle")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Book book=response.body().as(Book.class);

        assertNotNull(book);
        assertEquals(book.getTitle(), myBook.getTitle());
    }

    @Test
    public void deleteBookRequest(){
        Mockito.doNothing().when(bookService).deleteBook(0L);
        Response response = given()
                .param("id",0)
                .when()
                .delete("/controller_book/deleteBook")
                .then()
                .statusCode(500)
                .extract()
                .response();


    }

    //borrowedbook controller test

    @Test
    void borrowedBookRequest(){
        Mockito.doNothing().when(borrowedBookService).borrowBook("tytul", "autor", "gatunek", "uzytkownik");
        given()
                .param("title", "tytul")
                .param("myUser", "uzytkownik")
                .when()
                .post("/controller_borrowed_book/borrowBook")
                .then()
                .statusCode(500);
    }

    //@Test
    void borrowedBookByUserRequest(){
        List<BorrowedBook> borrowedBookList = null;
        Mockito.when(borrowedBookService.searchBookByUser("uzytkownik")).thenReturn(borrowedBookList);
        Response response = given()
                .param("myUser", "uzytkownik")
                .when()
                .get("/controller_borrowed_book/borrowByUser")
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<BorrowedBook> myBorrowedBookList= (List<BorrowedBook>) response.body().as(BorrowedBook.class);

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

    //user controller test

    @Test
    void searchUserRequest(){
        MyUser myUser=new MyUser();
        Mockito.when(userService.searchUser("uzytkownik")).thenReturn(myUser);
        Response response = given()
                .param("myUser", "uzytkownik")
                .when()
                .get("controller_user/searchUser")
                .then()
                .statusCode(200)
                .extract()
                .response();

        MyUser user=response.body().as(MyUser.class);

        assertNotNull(user);
        assertEquals(user.getMyUser(), myUser.getMyUser());
    }

    //@Test
    void deleteUserRequest(){
        MyUser myUser=new MyUser();
        Mockito.when(userService.deleteUser("uzytkownik")).thenReturn(myUser);
        Response response= given()
                .contentType(ContentType.JSON)
                .param("myUser", "uzytkownik")
                .when()
                .delete("/controller_user/deleteUser")
                .then()
                .statusCode(200)
                .extract()
                .response();

        MyUser user=response.body().as(MyUser.class);

        assertNotNull(user);
        assertEquals(user.getMyUser(), myUser.getMyUser());
    }

    @Test
    void addUserRequest(){
        MyUser myUser=new MyUser();
        Mockito.when(userService.addUser("uzytkownik")).thenReturn(myUser);
        Response response = given()
                .param("myUser", "uzytkownik")
                .when()
                .post("/controller_user/addUser")
                .then()
                .statusCode(200)
                .extract()
                .response();

        MyUser user=response.body().as(MyUser.class);

        assertNotNull(user);
        assertEquals(user.getMyUser(),myUser.getMyUser());
    }
}
