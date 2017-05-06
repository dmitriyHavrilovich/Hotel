package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.NaturalPerson;

public interface NaturalPersonRepository extends CrudRepository<NaturalPerson, Long> {

    NaturalPerson findBySurnameAndNameAndPatronymic(String surname, String name, String patronymic);
}
