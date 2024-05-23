package my_library.demo.services.my_user_service;

import my_library.demo.model.MyUser;

public interface UserServiceInterface {

    MyUser addUser(String myUser);
    MyUser deleteUser(String myUser);
    MyUser searchUser(String myUser);

    void changeAmountForUser(String myUser);


}
