package my_library.demo.services.my_user_service;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import my_library.demo.exceptions.MyException;
import my_library.demo.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserServiceInterface{

    @Autowired
    private MyUserRepository myUserRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MyUser addUser(String myUser) {
        if(searchUser(myUser)==null){
            MyUser myUser1=new MyUser();
            myUser1.setMyUser(myUser);
            myUser1.setAmountOfBooks(0);
            myUserRepository.save(myUser1);
            return myUser1;
        }
        log.info("Istnieje już użytkownik o loginie "+myUser);
        throw new MyException("Isnieje już użytkownik o loginie "+myUser);
    }

    @Override
    public MyUser deleteUser(String myUser) {
        if (searchUser(myUser)==null){
            log.info("Nie ma w bazie użytkownika o loginie "+myUser+" więc nie można go usunąć");
            throw new MyException("Nie ma w bazie użytkownika o loginie "+myUser+" więc nie można go usunąć");
        }
        else {
            MyUser myUser1=searchUser(myUser);
            if(myUser1.getAmountOfBooks()!=0){
                log.info("Masz wypożyczone książki, więc najpierw musisz je oddać, żeby móc usunąć konto");
                throw new MyException("Masz wypożyczone książki, więc najpierw musisz je oddać, żeby móc usunąć konto");
            }
            else {
                myUserRepository.deleteById(myUser1.getId());
                log.info("Usunieto z bazy użytkownika o loginie "+myUser);
                return myUser1;
            }
        }
    }

    @Override
    public MyUser searchUser(String myUser) {
        try{
            TypedQuery<MyUser> query= entityManager.createQuery("SELECT user FROM MyUser user WHERE user.myUser = :sUser", MyUser.class).setParameter("sUser", myUser);
            return query.getSingleResult();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void changeAmountForUser(String myUser){
        MyUser myUser1=searchUser(myUser);
        myUser1.setAmountOfBooks(myUser1.getAmountOfBooks()+1);
        myUserRepository.save(myUser1);
    }


}
