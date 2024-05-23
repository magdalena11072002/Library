package my_library.demo.controller;

import my_library.demo.services.my_user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controller_user")
public class ControllerUser {

    @Autowired
    private UserService userService;

    @GetMapping("/searchUser")
    public ResponseEntity searchUser(@RequestParam String myUser){
        try{
            return ResponseEntity.status(200).body(userService.searchUser(myUser));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam String myUser){
        try {
            userService.deleteUser(myUser);
            return ResponseEntity.status(200).body("Użytkownik o loginie "+myUser+ " został usunięty z bazy");
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestParam String myUser){
        try{
            return ResponseEntity.status(200).body(userService.addUser(myUser));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

}
