package ua.iasa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.entity.Contractor;
import ua.iasa.entity.Document;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
class ReferencesDocuments{
    @PersistenceContext
    EntityManager entityManager;
    //public Set<Document> getReferencesOfDocument() {
        /*Query query = entityManager.createNativeQuery("select d.* from PUBLIC.document d," +
             //   " INNER JOIN contractor c  " +плюс Product
          //      "where c.id = b.contr_id " , Document.class);

        return query.getResult();*/
    //}
}
