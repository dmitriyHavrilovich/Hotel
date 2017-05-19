package ua.iasa.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.ui.entity.ReferenceDocument;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
@RequiredArgsConstructor
public class ReferenceDocumentsDao {

    private final EntityManager entityManager;

    private Session getSession() {
        return (Session) entityManager.getDelegate();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public Set<ReferenceDocument> getReferencesOfDocuments() {
        List<ReferenceDocument> documents =
                (List<ReferenceDocument>) getSession().createSQLQuery("SELECT\n" +
                        " document.id,\n" +
                        " document_type.doc_type,\n" +
                        " document.currency,\n" +
                        " document.date,\n" +
                        " contractor.name,\n" +
                        " product.name_type,\n" +
                        " product.amount,\n" +
                        " product.price,\n" +
                        " personal.namep\n " +
                        "FROM document\n" +
                        "  LEFT JOIN contractor ON document.contractor_id = contractor.id\n" +
                        "LEFT JOIN personal ON document.personal_id = personal.id\n" +
                        "  LEFT JOIN product ON document.id = product.document_id\n" +
                        "LEFT JOIN document_type ON document.type_doc_id = document_type.id")
                        .addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("doc_type")
                        .addScalar("currency")
                        .addScalar("date")
                        .addScalar("name")
                        .addScalar("name_type")
                        .addScalar("amount", StandardBasicTypes.DOUBLE)
                        .addScalar("price", StandardBasicTypes.DOUBLE)
                        .addScalar("namep")
                        .setResultTransformer(new AliasToBeanResultTransformer
                                (ReferenceDocument.class)).list();
        return new HashSet<>(documents);
    }


}
