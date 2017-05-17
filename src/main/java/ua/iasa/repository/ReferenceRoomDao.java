package ua.iasa.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.ui.entity.ReferenceDocument;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
@RequiredArgsConstructor

public class ReferenceRoomDao {
    private final EntityManager entityManager;

    private Session getSession(){
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
                        "  room_product.amount,\n" +
                       // "  product.measure,\n" +
                        "  room_product.price\n" +
                        "FROM room\n" +
                        "  LEFT JOIN room_product" +
                        " ON room.id = room_product.room_id")
                        .addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("amount",StandardBasicTypes.DOUBLE)
                        .addScalar("room_type").addScalar("number").addScalar("name_type")
                       // .addScalar("measure",StandardBasicTypes.DOUBLE)
                        .addScalar("price", StandardBasicTypes.DOUBLE)
                        .setResultTransformer(new AliasToBeanResultTransformer
                                (ReferenceRoom.class)).list();
        return new HashSet<>(rooms);
    }

    public void MoveProduct(String sourceRoom, String targetRoom,
                            String product, Double amount){
         getSession().createSQLQuery("SELECT move_product(:sourceRoom," +
                ":targetRoom, :product, :amount);")
                 .setParameter(sourceRoom, sourceRoom)
                 .setParameter(targetRoom, targetRoom)
                 .setParameter(product, product)
                 .setParameter(amount.toString(), amount);

    }
}
