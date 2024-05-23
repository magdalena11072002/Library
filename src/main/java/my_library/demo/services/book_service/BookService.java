package my_library.demo.services.book_service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import my_library.demo.exceptions.MyException;
import my_library.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService implements BookServiceInterface {

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book searchBookByTitle(String title) {
        try{
            TypedQuery<Book> query = entityManager.createQuery("SELECT book FROM Book book WHERE book.title = :searchTitle", Book.class).setParameter("searchTitle", title);
            return query.getSingleResult();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> searchBookByAuthor(String author) {
        try{
            TypedQuery<Book> query= entityManager.createQuery("SELECT book FROM Book book WHERE book.author = :searchAuthor", Book.class).setParameter("searchAuthor", author);
            return query.getResultList();
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new MyException("Nie ma w bibliotece książek autorstwa "+author);
        }

    }

    @Override
    public List<Book> searchBookByGenre(String genre) {
        try{
            TypedQuery<Book> query= entityManager.createQuery("SELECT book FROM Book book WHERE book.genre = :searchGenre", Book.class).setParameter("searchGenre", genre);
            return query.getResultList();
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new MyException("Nie ma w bibliotece książki z gatunku "+genre);
        }
    }

    @Override
    public Optional<Book> searchBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new MyException("Nie ma w bibliotece książki o id  "+id);
        }
        return book;
    }

    @Override
    public Book addBook(String title, String author, String genre) {
        if(searchBookByTitle(title)!=null){
            Book book=searchBookByTitle(title);
            book.setAmount(book.getAmount()+1);
            bookRepository.save(book);
            throw new MyException("Taka książka już istnieje w bazie więc jedynie zwiększamy jej ilość");
        }
        Book book=new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setAmount(book.getAmount()+1);
        bookRepository.save(book);
        return book;
    }


    @Override
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

}
