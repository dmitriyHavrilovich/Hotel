package ua.iasa.repository;


import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
