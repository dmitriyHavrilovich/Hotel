package ua.iasa.repository;


import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Contractor;

public interface ContractorRepository extends CrudRepository<Contractor, Long> {


    Contractor findByName(String name);
}
