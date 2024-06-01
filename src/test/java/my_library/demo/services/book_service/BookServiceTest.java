package my_library.demo.services.book_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

        @BeforeEach
        void cleanDB(){
            bookRepository.deleteAll();
        }

        @Test
        void shouldAddBookToDB(){
            //given
            String title = "tytul";
            String author = "autor";
            String genre = "gatunek";

            //when
            bookService.addBook(title,author,genre);

            //then
            Assertions.assertEquals(1,bookRepository.count());
        }

//        @Test
//        void shouldFindBookInDB(){
//            //given
//            String title = "tytul";
//            String author="autor";
//            String genre="gatunek";
//            Book book=bookService.addBook(title,author,genre);
//
//            bookRepository.save(book);
//
//            Assertions.assertEquals(book, bookRepository.findById(book.getId()));
//        }
}
