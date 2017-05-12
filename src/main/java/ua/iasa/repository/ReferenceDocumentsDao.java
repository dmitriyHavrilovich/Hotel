package ua.iasa.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.ui.entity.ReferenceDocument;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ReferenceDocumentsDao {

    private final EntityManager entityManager;

    private Session getSession(){
        return (Session) entityManager.getDelegate();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public List<ReferenceDocument> getReferencesOfDocuments() {
          List<ReferenceDocument> documents =
                  (List<ReferenceDocument>) getSession().createSQLQuery("SELECT\n" +
                "  document.id,\n" +
                "  type_of_document.doc_type,\n" +
                "  document.currency,\n" +
                "  document.date,\n" +
                "  contractor.name,\n" +
                "  product.name_type,\n" +
                "  product.amount,\n" +
                "  product.measure,\n" +
                "  product.price\n" +
                "FROM document\n" +
                "  LEFT JOIN contractor ON document.contr_id = contractor.id\n" +
                "  LEFT JOIN product ON document.id = product.document_id\n" +
                "LEFT JOIN type_of_document ON document.type_doc_id = type_of_document.id").setResultTransformer(new AliasToBeanResultTransformer(ReferenceDocument.class)).list();
          return documents;
    }
}
