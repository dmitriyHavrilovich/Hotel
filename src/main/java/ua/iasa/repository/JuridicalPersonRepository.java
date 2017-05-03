package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.JuridicalPerson;

/**
 * Created by Mahaon on 02.05.2017.
 */
public interface JuridicalPersonRepository extends CrudRepository<JuridicalPerson, Long> {
}
