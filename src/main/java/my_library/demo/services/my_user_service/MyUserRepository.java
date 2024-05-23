package my_library.demo.services.my_user_service;

import my_library.demo.model.MyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MyUserRepository extends CrudRepository<MyUser, Long> {


}
