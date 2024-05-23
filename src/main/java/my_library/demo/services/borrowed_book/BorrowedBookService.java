package my_library.demo.services.borrowed_book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import my_library.demo.exceptions.MyException;
import my_library.demo.model.Book;
import my_library.demo.model.BorrowedBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BorrowedBookService implements BorrowedBookServiceInterface{

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BorrowedBook searchBook(String title) {
        try{
            TypedQuery<BorrowedBook> query= entityManager.createQuery("SELECT book FROM BorrowedBook book WHERE book.title = :searchTitle", BorrowedBook.class).setParameter("searchTitle", title);
            return query.getSingleResult();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public BorrowedBook searchBookByTitleAndUser(String title, String myUser) {
        try{
            TypedQuery<BorrowedBook> query= entityManager.createQuery("SELECT book FROM BorrowedBook book WHERE book.title = :searchTitle AND book.myUser = :searchUser", BorrowedBook.class).setParameter("searchTitle", title).setParameter( "searchUser", myUser);
            return query.getSingleResult();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void borrowBook(String title, String author,String genre, String myUser){
        BorrowedBook borrowedBook=new BorrowedBook();
        borrowedBook.setTitle(title);
        borrowedBook.setMyUser(myUser);
        borrowedBook.setAuthor(author);
        borrowedBook.setGenre(genre);
        borrowedBook.setRentalDate(LocalDate.now());
        borrowedBook.setDateOfSubmission(LocalDate.now().plusMonths(1));
        borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public List<BorrowedBook> searchBookByUser(String myUser){
        try{
            TypedQuery<BorrowedBook> query= entityManager.createQuery("SELECT book FROM BorrowedBook book WHERE book.myUser = :searchUser", BorrowedBook.class).setParameter("searchUser", myUser);
            return query.getResultList();
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new MyException("Użytkownik o loginie  "+myUser+ " nie wypożyczył żadnej książki");
        }
    }

    @Override
    public void deleteBook(Long id){
        borrowedBookRepository.deleteById(id);
    }
}
