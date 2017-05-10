package ua.iasa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.entity.Contractor;
import ua.iasa.entity.Document;
import ua.iasa.ui.entity.ReferencesDocumentView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
@Component
public class ReferencesDocuments{
    @Autowired
    EntityManager entityManager;
    public List<ReferencesDocumentView> getReferencesOfDocument() {
        Query query = entityManager.createNativeQuery("SELECT public.document.*, public.contractor.name,"+
         "public.product.* FROM public.document, public.contractor, public.product"+
  "WHERE public.document.contr_id=public.contractor.id AND public.product.id=public.document.product_id;");

        return query.getResultList();
    }
}
