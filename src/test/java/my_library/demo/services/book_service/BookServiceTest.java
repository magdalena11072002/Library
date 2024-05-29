package my_library.demo.services.book_service;


import my_library.demo.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {

//    @Mock
//    BookRepository bookRepository;
//
//    @InjectMocks
//    BookService bookService;
//
//        @BeforeAll
//        void cleanDB(){
//            bookRepository.deleteAll();
//        }
//
//        @Test
//        void shouldAddBookToDB(){
//            //given
//            String title = "tytul";
//            String author="autor";
//            String genre="gatunek";
//            Book book=bookService.addBook(title,author,genre);
//
//            //when
//            bookRepository.save(book);
//
//            //then
//            Assertions.assertEquals(1, bookRepository.count());
//        }
//
//        @Test
//        void shouldFindBookInDB(){
//            //given
//            MockitoAnnotations.initMocks(this);
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
