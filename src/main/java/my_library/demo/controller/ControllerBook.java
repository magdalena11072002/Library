package my_library.demo.controller;

import lombok.extern.slf4j.Slf4j;
import my_library.demo.services.MainService;
import my_library.demo.services.book_service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controller-book")
@Slf4j
public class ControllerBook {

    @Autowired
    private BookService bookService;

    @Autowired
    private MainService mainService;


    @PostMapping("/addBook")
    public ResponseEntity addBook(@RequestParam String title, @RequestParam String author, @RequestParam String genre) {
        try{
            return ResponseEntity.status(200).body(bookService.addBook(title, author, genre));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/searchBook")
    public ResponseEntity searchBook(@RequestParam Long id){
        try{
            return ResponseEntity.status(200).body(bookService.searchBook(id));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/searchBookByTitle")
    public ResponseEntity searchBookByTitle(@RequestParam String title){
        try{
            return ResponseEntity.status(200).body(bookService.searchBookByTitle(title));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity deleteBook(@RequestParam Long id){
        try{
            mainService.deleteBookFromBooks(id);
            return ResponseEntity.status(204).build();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}
