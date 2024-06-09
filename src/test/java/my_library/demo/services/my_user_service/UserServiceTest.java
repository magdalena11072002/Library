package my_library.demo.services.my_user_service;

import my_library.demo.model.MyUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void cleanDB(){myUserRepository.deleteAll();}

    @Test
    void shouldAddUserToDB(){
        //given
        String myUser="uzytkwnik";

        //when
        MyUser user = userService.addUser(myUser);

        //then
        Assertions.assertEquals(1, myUserRepository.count());
        Assertions.assertEquals(myUser, user.getMyUser());
    }

    @Test
    void shouldDeleteUserFromDB(){
        //given
        String myUser="uzytkwnik";
        userService.addUser(myUser);

        //when
        userService.deleteUser(myUser);

        //then
        Assertions.assertEquals(0, myUserRepository.count());
    }

    @Test
    void shouldFindUserInDb(){
        //given
        String myUser="uzytkwnik";
        MyUser user = userService.addUser(myUser);

        //when
        MyUser myUser1=userService.searchUser(myUser);

        Assertions.assertEquals(user.getMyUser(), myUser1.getMyUser());
    }

    @Test
    void shouldChangeAmountForUser(){
        //given
        String myUser="uzytkwnik";
        MyUser user = userService.addUser(myUser);

        //when
        userService.changeAmountForUser(user.getMyUser());

        //then
        Assertions.assertEquals(1, userService.searchUser(user.getMyUser()).getAmountOfBooks());
    }
}
