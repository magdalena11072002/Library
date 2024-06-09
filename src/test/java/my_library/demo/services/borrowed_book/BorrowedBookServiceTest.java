package my_library.demo.services.borrowed_book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BorrowedBookServiceTest {

    @Autowired
    BorrowedBookService borrowedBookService;

    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    @BeforeEach
    void cleanDB(){borrowedBookRepository.deleteAll();}

    @Test
    void shouldFindBookInDB(){
        //given
        String title = "tytul";
        String author="autor";
        String genre="gatunek";
        String myUser="uzytkownik";

        //when
        borrowedBookService.borrowBook(title, author, genre, myUser);

        //then
        Assertions.assertEquals(title, borrowedBookService.searchBook(title).getTitle());
        Assertions.assertEquals(myUser, borrowedBookService.searchBookByTitleAndUser(title, myUser).getMyUser());
        Assertions.assertEquals(title,borrowedBookService.searchBookByUser(myUser).get(0).getTitle());
    }

    @Test
    void shouldDeleteBookFromDB(){
        //given
        String title = "tytul";
        String author="autor";
        String genre="gatunek";
        String myUser="uzytkownik";
        borrowedBookService.borrowBook(title, author, genre, myUser);

        //when
        borrowedBookService.deleteBook(borrowedBookService.searchBook(title).getId());

        //then
        Assertions.assertEquals(0,borrowedBookRepository.count());
    }
}
