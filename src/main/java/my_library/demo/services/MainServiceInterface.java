package my_library.demo.services;

import my_library.demo.model.Book;

import java.time.LocalDate;

public interface MainServiceInterface {

    void borrow(String title, String myUser);

    void deleteBookFromBooks(Long id);

    Book searchByTitle(String title);

    void returnBook(String title, String myUser);

}
