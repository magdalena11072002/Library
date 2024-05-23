package my_library.demo.services.borrowed_book;

import my_library.demo.model.BorrowedBook;
import org.springframework.data.repository.CrudRepository;

public interface BorrowedBookRepository extends CrudRepository<BorrowedBook, Long> {
}
