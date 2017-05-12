package ua.iasa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.ui.entity.ReferenceDocument;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ReferenceDocuments {

    private final EntityManager entityManager;

    public List<ReferenceDocument> getReferencesOfDocument() {
        Query query = entityManager.createNativeQuery("SELECT public.document.*, public.contractor.name," +
                "public.product.* FROM public.document, public.contractor, public.product " +
                "WHERE public.document.contr_id = PUBLIC.contractor.id " +
                "AND PUBLIC.product.id = PUBLIC.document.product_id", ReferenceDocument.class);

        return query.getResultList();
    }
}
