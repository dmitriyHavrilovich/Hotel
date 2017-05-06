package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.JuridicalPerson;

public interface JuridicalPersonRepository extends CrudRepository<JuridicalPerson, Long> {
    JuridicalPerson findByNameAndEdrpou(String name, String edrpou);
}
