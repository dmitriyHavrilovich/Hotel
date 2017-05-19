package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Personal;

/**
 * Created by Mahaon on 19.05.2017.
 */
public interface PersonalRepository extends CrudRepository<Personal,Long> {
    Personal findByNamep(String value);
}
