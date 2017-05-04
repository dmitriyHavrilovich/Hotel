package ua.iasa.repository;


import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.MovementDocument;

public interface MovementDocumentRepository extends CrudRepository<MovementDocument, Long> {
}
