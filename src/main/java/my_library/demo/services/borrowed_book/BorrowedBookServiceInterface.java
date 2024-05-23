package my_library.demo.services.borrowed_book;

import my_library.demo.model.Book;
import my_library.demo.model.BorrowedBook;

import java.util.List;
import java.util.Optional;

public interface BorrowedBookServiceInterface {

    BorrowedBook searchBook(String title);

    void borrowBook(String title,String author, String genre, String myUser);

    List<BorrowedBook> searchBookByUser(String myUser);

    void deleteBook(Long id);

    BorrowedBook searchBookByTitleAndUser(String title, String myUser);
}
