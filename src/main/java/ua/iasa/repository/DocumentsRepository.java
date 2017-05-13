package ua.iasa.repository;


import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Document;

public interface DocumentsRepository extends CrudRepository<Document, Long> {
}
