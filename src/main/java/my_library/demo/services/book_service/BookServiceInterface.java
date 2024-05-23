package my_library.demo.services.book_service;

import my_library.demo.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {

    // funckje do wyszukiwania dla użytkownika
    Book searchBookByTitle(String title);
    List<Book> searchBookByAuthor(String author);
    List<Book> searchBookByGenre(String genre);

    //funkcja dla systemu żeby sprawdzić czy książka jest na stanie
    Optional<Book> searchBook(Long id);

    Book addBook(String title, String author, String genre);

    void deleteBook(Long id);


}
