package my_library.demo.controller;


import lombok.extern.slf4j.Slf4j;
import my_library.demo.model.BorrowedBook;
import my_library.demo.services.MainService;
import my_library.demo.services.borrowed_book.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/controller_borrowed_book")
@Slf4j
public class ControllerBorrowedBook {

    @Autowired
    private BorrowedBookService borrowedBookService;

    @Autowired
    private MainService mainService;

    @PostMapping("/borrowBook")
    public ResponseEntity borrowBook(@RequestParam String title, @RequestParam String myUser){
        try{
            mainService.borrow(title,myUser);
            return ResponseEntity.status(200).body("Książka została wypożyczona do dnia "+ LocalDate.now().plusMonths(1));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/borrowByUser")
    public ResponseEntity borrowByUser(@RequestParam String myUser){
        try {
            List<BorrowedBook> borrowedBooks=borrowedBookService.searchBookByUser(myUser);
            return ResponseEntity.status(200).body("Lista wypożyczonych książek przez użytkownika "+myUser+"\n");
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PutMapping("/returnBook")
    public ResponseEntity returnBook(@RequestParam String title, @RequestParam String myUser){
        try {
            mainService.returnBook(title,myUser);
            return ResponseEntity.status(200).body("Ksiązka została oddana");
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }


}
