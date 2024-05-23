package my_library.demo.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import my_library.demo.exceptions.MyException;
import my_library.demo.model.Book;
import my_library.demo.model.BorrowedBook;
import my_library.demo.model.MyUser;
import my_library.demo.services.book_service.BookService;
import my_library.demo.services.borrowed_book.BorrowedBookService;
import my_library.demo.services.my_user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

@Service
@Slf4j
public class MainService implements MainServiceInterface{

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;
    @Autowired
    private BorrowedBookService borrowedBookService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book searchByTitle(String title) {
        try{
            TypedQuery<Book> query= entityManager.createQuery("SELECT book FROM Book book WHERE book.title = :searchTitle", Book.class).setParameter("searchTitle", title);
            return query.getSingleResult();
        }
        catch (Exception ex){
            throw new MyException("Nie ma w bibliotece książki o tytule "+title);
        }
    }

    @Override
    public void deleteBookFromBooks(Long id) {
        try{
            Book book=bookService.searchBook(id).get();
            if(borrowedBookService.searchBook(book.getTitle())==null){
                log.info("Książka nie jest wypozyczona, więc możemy ją usunąć z biblioteki");
                bookService.deleteBook(id);
                throw new MyException("Książka nie była wypozyczona, więc została usunięta z biblioteki "+id.toString());
            }
            else {
                log.info("Książka jest wypożyczona, więc nie możemy jej usunąc z biblioteki");
                throw new MyException("Książka jest wypożyczona, więc nie możemy jej usunąć "+id.toString());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            log.info("Nie istnieje obiekt o takim id, więc nie można go usunąć");
            throw new MyException("Nie ma w bibliotece książki o id "+ id.toString()+ "więc nie można jej usunąć");
        }
    }

    @Override
    public void borrow(String title, String myUser) {
        if(userService.searchUser(myUser)==null){
            log.info("Nie ma takiego uzytkownika. Najpierw załóż konto");
            throw new MyException("Nie ma w bazie użytkownika o loginie "+myUser);
        }
        if(userService.searchUser(myUser).getAmountOfBooks()>4){
            log.info("Użytkownik o loginie "+myUser+" ma wypożyczone 5 książek i nie może wypożyczyć kolejnej bo przekroczy limit ");
            throw new MyException("użytkownik o loginie "+myUser+" ma wypożyczone 5 książek i nie może wypożyczyć kolejnej bo przekroczy limit");
        }
        Book book=bookService.searchBookByTitle(title);
        if(book==null){
            log.info("Nie ma takiej ksiązki w bibliotece, więc nie można jej wypozyczyć");
            throw new MyException("Nie ma w bibliotece książki o tytule "+title +" więc nie można jej wypożyczyć");
        }
        if (borrowedBookService.searchBookByTitleAndUser(title,myUser)!=null){
            if (Objects.equals(borrowedBookService.searchBookByTitleAndUser(title,myUser).getMyUser(), myUser)){
                log.info("Użytkownik o loginie "+ myUser+ " ma już wypożyczoną książkę o tytule "+ title+ " więc nie może jej wypożyczyć jeszcze raz");
                throw new MyException("Użytkownik o loginie "+ myUser+ " ma już wypożyczoną książkę o tytule" + title+ " więc nie może jej wypożyczyć jeszcze raz");
            }
        }
        if (book.getAmount()==0){
            log.info("Niestety nie mamy tej książki na stanie");
            throw new MyException("Książka o tytule "+title+" została wypożyczona i nie ma jej na stanie");
        }
        if(book.getAmount()==1){
            bookService.deleteBook(book.getId());
            borrowedBookService.borrowBook(title,book.getAuthor(), book.getGenre(), myUser);
            userService.changeAmountForUser(myUser);
            return;
        }
        book.setAmount(book.getAmount()-1); //zmniejszamy ilośc książek w bibliotece
        borrowedBookService.borrowBook(title,book.getAuthor(), book.getGenre(), myUser);
        userService.changeAmountForUser(myUser);
    }

    @Override
    public void returnBook(String title, String myUser){
        if(userService.searchUser(myUser)==null){ //czy istnieje użytkownik
            log.info("Nie ma takiego uzytkownika. Najpierw załóż konto");
            throw new MyException("Nie ma w bazie użytkownika o loginie "+myUser);
        }
        BorrowedBook borrowedBook=borrowedBookService.searchBookByTitleAndUser(title,myUser);
        MyUser myUser1=userService.searchUser(myUser);
        if(myUser1.getAmountOfBooks()==0){ //czy użytkownik ma wgl wypożyczone książki
            log.info("Użytkownik o loginie "+myUser+ " nie ma wypożyczonej żadnej książki");
            throw new MyException("Użytkownik o loginie "+myUser+ " nie ma wypożyczonej żadnej książki");
        }
        if (borrowedBookService!=null){
            if (!Objects.equals(borrowedBookService.searchBookByTitleAndUser(title,myUser).getMyUser(), myUser)){
                log.info("Książka o tytule "+title+" nie jest wypożyczona przez użytkownika o loginie "+myUser);
                throw new MyException("Książka o tytule "+title+" nie jest wypożyczona przez użytkownika o loginie "+myUser);
            }
        }
        else {
            log.info("Żaden uzytkownik nie wypozyczył ksiazki o tytule "+title);
            throw new MyException("Żaden uzytkownik nie wypozyczył ksiazki o tytule "+title);
        }
        if (borrowedBook.getDateOfSubmission().isAfter(LocalDate.now())){ //naliczamy odsetki
            log.info("Minęła data oddania książki. Użytkownikowi o loginie "+myUser+" zostają naliczone odstetki");
            Duration difference=Duration.between(borrowedBook.getDateOfSubmission(),LocalDate.now());
            Long interest=difference.toDays()*3;
            myUser1.setInterest(interest);
        }
        if(bookService.searchBookByTitle(title)==null){ //jeśli ksiązki nie ma w tablicy books to musimy ją dodać spowrotem
            bookService.addBook(title, borrowedBook.getAuthor(), borrowedBook.getGenre());
            myUser1.setAmountOfBooks(myUser1.getAmountOfBooks()-1);
            borrowedBookService.deleteBook(borrowedBook.getId());
            return;
        }
        bookService.searchBookByTitle(title).setAmount(bookService.searchBookByTitle(title).getAmount()+1);
        myUser1.setAmountOfBooks(myUser1.getAmountOfBooks()-1);
        borrowedBookService.deleteBook(borrowedBook.getId());

    }
}
