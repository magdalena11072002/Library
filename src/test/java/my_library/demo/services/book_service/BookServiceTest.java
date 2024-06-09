package my_library.demo.services.book_service;

import my_library.demo.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        @Test
        void shouldFindBookInDB(){
            //given
            String title = "tytul";
            String author="autor";
            String genre="gatunek";
            Book book=bookService.addBook(title,author,genre);

            //when
            bookRepository.save(book);

            //then
            Assertions.assertEquals(book.getId(), bookRepository.findById(book.getId()).get().getId());
            Assertions.assertEquals(book.getTitle(), bookRepository.findById(book.getId()).get().getTitle());
            Assertions.assertEquals(book.getAuthor(),bookRepository.findById(book.getId()).get().getAuthor());
            Assertions.assertEquals(book.getGenre(), bookRepository.findById(book.getId()).get().getGenre());
        }

        @Test
        void shouldFingBookInDbByTitleByAutorByGenre(){
            //given
            String title = "tytul";
            String author="autor";
            String genre="gatunek";
            Book book=bookService.addBook(title,author,genre);

            //when
            bookRepository.save(book);

            //then
            Assertions.assertEquals(book.getTitle(), bookService.searchBookByTitle("tytul").getTitle());
            Assertions.assertEquals(book.getAuthor(), bookService.searchBookByAuthor("autor").get(0).getAuthor());
            Assertions.assertEquals(book.getGenre(), bookService.searchBookByGenre("gatunek").get(0).getGenre());
        }

        @Test
        void shouldDeleteBookFromDB(){
            //given
            String title = "tytul";
            String author="autor";
            String genre="gatunek";
            Book book=bookService.addBook(title,author,genre);
            bookRepository.save(book);

            //when
            bookService.deleteBook(book.getId());

            //then
            Assertions.assertEquals(0,bookRepository.count());
        }
}
