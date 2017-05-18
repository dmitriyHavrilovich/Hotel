package ua.iasa.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
@RequiredArgsConstructor

public class ReferenceRoomDao {
    private final EntityManager entityManager;

    private Session getSession() {
        return (Session) entityManager.getDelegate();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public Set<ReferenceRoom> getReferencesOfRoom() {

        List<ReferenceRoom> rooms =
                (List<ReferenceRoom>) getSession().createSQLQuery("SELECT\n" +
                        "  room.id,\n" +
                        "  room.room_type,\n" +
                        "  room.number,\n" +
                        "  room_product.name_type,\n" +
                        "  room_product.amount\n" +
                        // "  product.measure,\n" +
                        // "  room_product.price\n" +
                        "FROM room \n" +
                        "  LEFT JOIN room_product \n" +
                        " ON room.id = room_product.room_id \n")
                        .addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("amount", StandardBasicTypes.DOUBLE)
                        .addScalar("room_type").addScalar("number").addScalar("name_type")
                        // .addScalar("measure",StandardBasicTypes.DOUBLE)
                        // .addScalar("price", StandardBasicTypes.DOUBLE)
                        .setResultTransformer(new AliasToBeanResultTransformer
                                (ReferenceRoom.class)).list();
        return new HashSet<>(rooms);
    }

    public void moveProduct(String sourceRoom, String targetRoom,
                            String product, Double amount) {
        entityManager.createNativeQuery("SELECT move_product(:sourceRoom, :targetRoom, :product, :amount) as text")
                .setParameter("sourceRoom", sourceRoom)
                .setParameter("targetRoom", targetRoom)
                .setParameter("product", product)
                .setParameter("amount", amount).getSingleResult();
//        StoredProcedureQuery query = entityManager
//                .createStoredProcedureQuery("move_product")
//                .registerStoredProcedureParameter("sourceRoom",
//                        String.class, ParameterMode.IN)
//                .registerStoredProcedureParameter("targetRoom",
//                        String.class, ParameterMode.IN)
//                .registerStoredProcedureParameter("product_name",
//                        String.class, ParameterMode.IN)
//                .registerStoredProcedureParameter("amounts",
//                        Double.class, ParameterMode.IN)
//                .registerStoredProcedureParameter(Void.class, ParameterMode.OUT)
//                .setParameter("sourceRoom", sourceRoom)
//                .setParameter("targetRoom", targetRoom)
//                .setParameter("product_name",product)
//                .setParameter("amounts", amount);
//        query.execute();
    }
}
